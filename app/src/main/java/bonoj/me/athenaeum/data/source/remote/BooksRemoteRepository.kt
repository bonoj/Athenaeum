package bonoj.me.athenaeum.data.source.remote

import android.content.Context
import android.util.Log
import bonoj.me.athenaeum.BuildConfig
import bonoj.me.athenaeum.data.Book
import bonoj.me.athenaeum.data.BookDetails
import bonoj.me.athenaeum.data.BooksDataSource
import bonoj.me.athenaeum.data.model.ImageLinks
import bonoj.me.athenaeum.data.model.IndustryIdentifier
import io.reactivex.Single

class BooksRemoteRepository(private val context: Context) : BooksDataSource {

    private val booksApiService = BooksApiUtils.apiService
    private val apiKey = BuildConfig.API_KEY
    private var searchString = "science fiction"
    private val searchStrings = arrayListOf("fantasy", "horror", "history", "science")
    private var startIndex = 0
    private var isEndOfSearch = false

    override val books: Single<List<Book>>
        get() {

            return Single.fromCallable {

                requestBooksFromApi()
            }
        }

    override fun getBookDetails(id: String): Single<BookDetails> {

        return Single.fromCallable { requestDetailsFromApi(id) }
    }

//    private fun checkNotNull(unit: Unit) : Boolean {
//        if (unit != null)
//    }

    private fun requestDetailsFromApi(id: String): BookDetails {

//        var title: String = ""
//        var authors: List<String> = ArrayList()
//        var publisher: String = ""
//        var publishedDate: String = ""
//        var description: String = ""
//        var industryIdentifiers: List<IndustryIdentifier> = ArrayList()
//        var pageCount: Int = 0
//        var printType: String = ""
//        var categories: List<String> = ArrayList()
//        var averageRating: Double = 0.0
//        var ratingsCount: Int = 0
//        var maturityRating: String = ""
//        var imageLinks: ImageLinks = ImageLinks("", "")
//        var language: String = ""
//        var previewLink: String = ""

        val response = booksApiService.setId(id).execute()

        Log.i("GOOD", "response good")

        val volumeInfo = response.body()?.volumeInfo

        Log.i("GOOD", "volumeInfo good")

        val title: String = volumeInfo!!.title
        val authors: List<String> = volumeInfo.authors ?: ArrayList()
        val publisher: String = volumeInfo.publisher ?: ""
        val publishedDate: String = volumeInfo.publishedDate ?: ""
        val description: String = volumeInfo.description ?: "No description."
        val industryIdentifiers: List<IndustryIdentifier> = volumeInfo.industryIdentifiers ?: ArrayList()
        val pageCount: Int = volumeInfo.pageCount ?: 0
        val printType: String = volumeInfo.printType ?: ""
        val categories: List<String> = volumeInfo.categories ?: ArrayList()
        val averageRating: Double = volumeInfo.averageRating ?: 0.0
        val ratingsCount: Int = volumeInfo.ratingsCount?: 0
        val maturityRating: String = volumeInfo.maturityRating ?: ""
        val imageLinks: ImageLinks = volumeInfo.imageLinks
        val language: String = volumeInfo.language ?: ""
        val previewLink: String = volumeInfo.previewLink ?: ""

        if(description == null) {
            Log.i("LIES", "description is null")
        } else {
            Log.i("LIES", description)
        }

//        if (volumeInfo != null) {
//
//            title = volumeInfo.title
//            imageLinks = volumeInfo.imageLinks
//
//            Log.i("GOOD", "title and imageLinks good")
//
//            try {
//                authors = volumeInfo.authors
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "authors" + authors)
//            try {
//                publisher = volumeInfo.publisher
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "publisher" + publisher)
//            try {
//                publishedDate = volumeInfo.publishedDate
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "publishedDate" + publishedDate)
//            try {
//                description = volumeInfo.description
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "description" + description)
//            try {
//                industryIdentifiers = volumeInfo.industryIdentifiers
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "industryIdentifiers" + industryIdentifiers.toString())
//            try {
//                pageCount = volumeInfo.pageCount
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "pageCount" + pageCount)
//            try {
//                printType = volumeInfo.printType
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "printType" + printType)
//            try {
//                categories = volumeInfo.categories
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "categories" + categories.toString())
//            try {
//                averageRating = volumeInfo.averageRating
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "averageRating" + averageRating)
//            try {
//                ratingsCount = volumeInfo.ratingsCount
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "ratingsCount" + ratingsCount)
//            try {
//                maturityRating = volumeInfo.maturityRating
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "maturityRating" + maturityRating)
//            try {
//                language = volumeInfo.language
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "language" + language)
//            try {
//                previewLink = volumeInfo.previewLink
//            } catch(e: Exception) {
//                // Ignore exception
//            }
//            Log.i("GOOD", "previewLink" + previewLink)
//        }

//        return BookDetails(
//                title = title,
//                authors = authors,
//                publisher = publisher,
//                publishedDate = publishedDate,
//                description = description,
//                industryIdentifiers = industryIdentifiers,
//                pageCount = pageCount,
//                printType = printType,
//                categories = categories,
//                averageRating = averageRating,
//                ratingsCount = ratingsCount,
//                maturityRating = maturityRating,
//                imageLinks = imageLinks,
//                language = language,
//                previewLink = previewLink)

        return BookDetails(
                title = title,
                authors = authors,
                publisher = publisher,
                publishedDate = publishedDate,
                description = description,
                industryIdentifiers = industryIdentifiers,
                pageCount = pageCount,
                printType = printType,
                categories = categories,
                averageRating = averageRating,
                ratingsCount = ratingsCount,
                maturityRating = maturityRating,
                imageLinks = imageLinks,
                language = language,
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
