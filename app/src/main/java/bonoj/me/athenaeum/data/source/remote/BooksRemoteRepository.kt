package bonoj.me.athenaeum.data.source.remote

import android.content.Context
import android.util.Log
import bonoj.me.athenaeum.BuildConfig
import bonoj.me.athenaeum.data.Book
import bonoj.me.athenaeum.data.BooksDataSource
import io.reactivex.Single
import java.util.*

class BooksRemoteRepository(private val context: Context) : BooksDataSource {

    private val apiKey = BuildConfig.API_KEY

    private val booksApiService = BooksApiUtils.apiService

    private var startIndex = 0
    private var searchString = "apple"

    override val books: Single<List<Book>>
        get() {

            //Log.i("MVP model", "API key = " + apiKey)

            return Single.fromCallable {

                var booksFromApi = getBooksFromApi()

                if (booksFromApi.isEmpty()) {

                    Log.i("Google Books API", "Size of booksFromApi: " + booksFromApi.size.toString())

                    startIndex = 0
                    searchString = "banana"
                    booksFromApi = getBooksFromApi()
                }

                booksFromApi
            }
        }

    // TODO Move the list growth from view to model

    private fun getBooksFromApi(): List<Book> {

        val books = ArrayList<Book>()

        val response = booksApiService.setParameters(startIndex, searchString).execute()

        Log.i("Google Books API", booksApiService.setParameters(startIndex, searchString).request().url().toString())

        startIndex += 740

        Log.i("Google Books API", response.body().toString())

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

        Log.i("Google Books API", "Returning " + books.size.toString())
        return books
    }

    fun incrementIndex(currentIndex: Int): Int {
        return currentIndex + 40
    }
}
