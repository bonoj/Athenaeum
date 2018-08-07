package bonoj.me.athenaeum.data.source

import android.content.Context
import bonoj.me.athenaeum.BuildConfig
import bonoj.me.athenaeum.data.model.Book
import bonoj.me.athenaeum.data.model.BookDetails
import bonoj.me.athenaeum.data.model.ImageLinks
import bonoj.me.athenaeum.data.source.remote.BooksApiUtils
import io.reactivex.Single
import kotlinx.coroutines.experimental.async


class BooksRepository(private val context: Context) : BooksDataSource {

    // Local
    val bookDao: BookDao
    val localBooks = ArrayList<Book>()

    init {
        val bookDatabase = BookDatabase.getInstance(context)
        bookDao = bookDatabase!!.bookDao()
        async {
            localBooks.addAll(bookDao.getBooks())
        }
    }

    fun insert(book: Book) {
        async {
            if (!localBooks.contains(book)) {
                bookDao.insert(book)
           }
        }
    }

    // Remote
    private val booksApiService = BooksApiUtils.apiService
    private val apiKey = BuildConfig.API_KEY
    private var searchString = "science fiction"
    private val searchStrings = arrayListOf("fantasy", "horror", "history", "science")
    private var startIndex = 0
    private var isEndOfSearch = false
    private var gotCache = false

    override val books: Single<List<Book>>
        get() {
            return Single.fromCallable {

                if (!gotCache && localBooks.isNotEmpty()) {
                    gotCache = true
                    startIndex = localBooks.size
                    return@fromCallable localBooks
                }
                return@fromCallable requestBooksFromApi() }
        }

    override fun getBookDetails(id: String): Single<BookDetails> {
        return Single.fromCallable { requestDetailsFromApi(id) }
    }

    override fun requestDetailsFromApi(id: String): BookDetails {

        val response = booksApiService.setId(id).execute()

        val volumeInfo = response.body()?.volumeInfo

        val title: String = volumeInfo!!.title
        val authors: List<String> = volumeInfo.authors ?: ArrayList()
        val publisher: String = volumeInfo.publisher ?: ""
        val publishedDate: String = volumeInfo.publishedDate ?: ""
        val dirtyDescription: String = volumeInfo.description ?: ""
        val pageCount: Int = volumeInfo.pageCount ?: 0
        val categories: List<String> = volumeInfo.categories ?: ArrayList()
        val averageRating: Double = volumeInfo.averageRating ?: 0.0
        val ratingsCount: Int = volumeInfo.ratingsCount?: 0
        val imageLinks: ImageLinks = volumeInfo.imageLinks
        val previewLink: String = volumeInfo.previewLink ?: ""

        val description = BooksApiUtils.getCleanDescriptionString(dirtyDescription)
        val authorString = BooksApiUtils.getAuthorString(authors)
        val publisherString = BooksApiUtils.getPublisherString(context, publisher, publishedDate)
        val pageString = BooksApiUtils.getPageString(context, pageCount)
        val categoriesString = BooksApiUtils.getCategoriesString(categories)
        val ratingsString = BooksApiUtils.getRatingsString(context, averageRating, ratingsCount)

        return BookDetails(
                title = title,
                authorString = authorString,
                publisherString = publisherString,
                pageString = pageString,
                categoriesString = categoriesString,
                ratingsString = ratingsString,
                description = description,
                imageLinks = imageLinks,
                previewLink = previewLink)
    }

    private fun requestBooksFromApi(): List<Book> {

        var booksFromApi: List<Book> = ArrayList()

        while (booksFromApi.isEmpty() && !isEndOfSearch) {

            booksFromApi = queryApi(startIndex, searchString)

            if (isEndOfSearch) {
                break
            }
        }

        startIndex += 40

        // Cache locally
        booksFromApi.forEach { insert(it) }

        return booksFromApi
    }

    private fun queryApi(startIndex: Int, searchString: String): List<Book> {

        val books = ArrayList<Book>()

        val response = booksApiService.setParameters(startIndex, searchString, apiKey).execute()

        val items = response.body()?.items

        if (items != null) {

            for (item in items) {

                try {
                    val id = item.id
                    val title = item.volumeInfo.title
                    val imageUrl = item.volumeInfo.imageLinks.thumbnail

                    books.add(Book(id, title, imageUrl))
                } catch (e: Exception) {
                    // Ignore book if it has no id, title, or imageUrl
                }
            }
        } else {
            this.startIndex = 0
            setSearchString()
        }

        return books
    }

    private fun setSearchString() {

        if (searchStrings.size > 0) {
            searchString = searchStrings[0]
            searchStrings.removeAt(0)
        } else {
            isEndOfSearch = true
        }
    }
}
