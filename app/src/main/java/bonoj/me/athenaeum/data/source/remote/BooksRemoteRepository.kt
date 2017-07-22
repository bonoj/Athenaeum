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
    private var searchString = "apple"

    override val books: Single<List<Book>>
        get() {

            //Log.i("MVP model", "API key = " + apiKey)

            return Single.fromCallable {

//                var booksFromApi = getBooksFromApi()
//
//                Log.i("Google Books API", booksFromApi.size.toString())
//                if (booksFromApi.isEmpty()) {
//                    startIndex = 0
//                    searchString = "banana"
//                    booksFromApi = getBooksFromApi()
//                }
//
//
//                booksFromApi

                val books = getBooksFromApi()

                books
            }
        }

    private fun getBooksFromApi(): List<Book> {

        val books = ArrayList<Book>()

        //val response = booksApiService.books.execute()

        val response = booksApiService.setParameters(startIndex, searchString).execute()

        Log.i("Google Books API", booksApiService.setParameters(startIndex, searchString).request().url().toString())

        startIndex += 40

        Log.i("Google Books API", response.body().toString())

        if (response.body()?.items == null) {
            startIndex = 0
            //searchString = "banana"

            return getBooksFromApi()
        } else {
            //val items = response.body()?.items
            response.isSuccessful.let {

                val booksApiResponse = response.body()

                booksApiResponse?.let {


                    val items = booksApiResponse.items

                    for (item in items) {

                        try {
                            val id = item.id
                            val title = item.volumeInfo.title
                            val imageUrl = item.volumeInfo.imageLinks.thumbnail

                            //Log.i("Google Books API", id + " " + title + " " + imageUrl)

                            books.add(Book(id, title, imageUrl))
                        } catch (e: Exception) {
                            // Skip the book if id, title, or imageUrl is null
                        }
                    }
                }
            }
            //return books
        }
        return books
    }
}
