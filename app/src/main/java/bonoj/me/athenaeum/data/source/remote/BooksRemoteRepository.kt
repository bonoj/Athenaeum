package bonoj.me.athenaeum.data.source.remote

import android.content.Context
import bonoj.me.athenaeum.BuildConfig
import bonoj.me.athenaeum.data.Book
import bonoj.me.athenaeum.data.BooksDataSource
import io.reactivex.Single

class BooksRemoteRepository(private val context: Context) : BooksDataSource {

    private val apiKey = BuildConfig.API_KEY

    private val booksApiService = BooksApiUtils.apiService

    private var startIndex = 0
    private var searchString = "apple"

    private var booksFromApi: ArrayList<Book> = ArrayList()

    override val books: Single<List<Book>>
        get() {

            return Single.fromCallable {

                var booksFromApi = getBooksFromApi(startIndex, searchString)

                if (booksFromApi.isEmpty()) {

                    startIndex = 0
                    searchString = "banana"

                    booksFromApi = getBooksFromApi(startIndex, searchString)
                }

                startIndex += 40
                booksFromApi
            }
        }

    private fun getBooksFromApi(startIndex: Int, searchString: String): List<Book> {

        val books = ArrayList<Book>()

        val response = booksApiService.setParameters(startIndex, searchString).execute()

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
        }
        
        return books
    }
}
