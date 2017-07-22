package bonoj.me.athenaeum.data.source.remote

import android.content.Context
import android.util.Log
import bonoj.me.athenaeum.BuildConfig
import bonoj.me.athenaeum.data.Book
import bonoj.me.athenaeum.data.BooksDataSource
import io.reactivex.Single
import java.util.*

class BooksRemoteRepository(private val context: Context) : BooksDataSource {

    // TODO Remove if apiKey not needed
    private val apiKey = BuildConfig.API_KEY

    private val booksApiService = BooksApiUtils.apiService
    private var startIndex = 0

    override val books: Single<List<Book>>
        get() {

            Log.i("MVP model", "API key = " + apiKey)

            return Single.fromCallable {

                getBooksFromApi()
            }
        }

    private fun getBooksFromApi(): List<Book> {

        val books = ArrayList<Book>()

        //val response = booksApiService.books.execute()

        val response = booksApiService.getStartIndex(startIndex).execute()

        Log.i("Google Books API", booksApiService.getStartIndex(startIndex).request().url().toString())

        startIndex += 45

//        if (startIndex >= 200) {
//            startIndex = 0
//        }


        response.isSuccessful.let {

            val booksApiResponse = response.body()

            booksApiResponse?.let {

                val items = booksApiResponse.items

                for (item in items) {

                    val id = item.id
                    val title = item.volumeInfo.title
                    val imageUrl = item.volumeInfo.imageLinks.thumbnail

//                    Log.i("Google Books API", id + " " + title + " " + imageUrl)

                    books.add(Book(id, title, imageUrl))
                }
            }
        }
        return books
    }
}
