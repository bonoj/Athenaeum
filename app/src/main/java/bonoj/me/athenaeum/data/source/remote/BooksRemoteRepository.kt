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

    override val books: Single<List<Book>>
        get() {

            Log.i("MVP model", "API key = " + apiKey)


//            val books = generateDummyBookList()

            return Single.fromCallable {

                getBooksFromApi()

//                println("Thread db: " + Thread.currentThread().id)
//
//                Log.i("MVP model", "getBooks returned " + books.size + " connections")
//
//                books
            }
        }

    private fun getBooksFromApi(): List<Book> {

        val books = ArrayList<Book>()
        val response = booksApiService.books.execute()

        response.isSuccessful.let {

            val booksApiResponse = response.body()

            booksApiResponse?.let {


                val items = booksApiResponse.items

                for (item in items) {

                    val id = item.id
                    val title = item.volumeInfo.title
                    val imageUrl = item.volumeInfo.imageLinks.thumbnail

                    Log.i("Retrofitters", id + " " + title + " " + imageUrl)
                    books.add(Book(id, title, imageUrl))
                }
            }

        }
        return books
    }

//    private fun generateDummyBookList(): List<Book> {
//        val books = ArrayList<Book>()
//        books.add(Book(id = "1", title = "The Fellowship of the Ring", imageUrl = "https://s-media-cache-ak0.pinimg.com/236x/65/75/59/657559342e32d10975bf99961ab084f0--fellowship-of-the-ring-lord-of-the-rings.jp"))
//        books.add(Book(id = "2", title = "The Two Towers", imageUrl = "https://img1.od-cdn.com/ImageType-400/0874-1/A1D/40F/DB/%7BA1D40FDB-55BF-4C91-BB96-3803FA25B575%7DImg400.jpg"))
//        books.add(Book(id = "3", title = "The Return of the King", imageUrl = "https://i.stack.imgur.com/071TF.jpg"))
//        books.add(Book(id = "4", title = "The Fellowship of the Ring", imageUrl = "https://s-media-cache-ak0.pinimg.com/236x/65/75/59/657559342e32d10975bf99961ab084f0--fellowship-of-the-ring-lord-of-the-rings.jpg"))
//        books.add(Book(id = "5", title = "The Two Towers", imageUrl = "https://img1.od-cdn.com/ImageType-400/0874-1/A1D/40F/DB/%7BA1D40FDB-55BF-4C91-BB96-3803FA25B575%7DImg400.jpg"))
//        books.add(Book(id = "6", title = "The Return of the King", imageUrl = "https://i.stack.imgur.com/071TF.jpg"))
//        books.add(Book(id = "7", title = "The Fellowship of the Ring", imageUrl = "https://s-media-cache-ak0.pinimg.com/236x/65/75/59/657559342e32d10975bf99961ab084f0--fellowship-of-the-ring-lord-of-the-rings.jpg"))
//        books.add(Book(id = "8", title = "The Two Towers", imageUrl = "https://img1.od-cdn.com/ImageType-400/0874-1/A1D/40F/DB/%7BA1D40FDB-55BF-4C91-BB96-3803FA25B575%7DImg400.jpg"))
//        books.add(Book(id = "9", title = "The Return of the King", imageUrl = "https://i.stack.imgur.com/071TF.jpg"))
//        books.add(Book(id = "10", title = "The Fellowship of the Ring", imageUrl = "https://s-media-cache-ak0.pinimg.com/236x/65/75/59/657559342e32d10975bf99961ab084f0--fellowship-of-the-ring-lord-of-the-rings.jpg"))
//        books.add(Book(id = "11", title = "The Two Towers", imageUrl = "https://img1.od-cdn.com/ImageType-400/0874-1/A1D/40F/DB/%7BA1D40FDB-55BF-4C91-BB96-3803FA25B575%7DImg400.jpg"))
//        books.add(Book(id = "12", title = "The Return of the King", imageUrl = "https://i.stack.imgur.com/071TF.jpg"))
//        books.add(Book(id = "13", title = "The Fellowship of the Ring", imageUrl = "https://s-media-cache-ak0.pinimg.com/236x/65/75/59/657559342e32d10975bf99961ab084f0--fellowship-of-the-ring-lord-of-the-rings.jpg"))
//        books.add(Book(id = "14", title = "The Two Towers", imageUrl = "https://img1.od-cdn.com/ImageType-400/0874-1/A1D/40F/DB/%7BA1D40FDB-55BF-4C91-BB96-3803FA25B575%7DImg400.jpg"))
//        books.add(Book(id = "15", title = "The Return of the King", imageUrl = "https://i.stack.imgur.com/071TF.jpg"))
//        books.add(Book(id = "16", title = "The Fellowship of the Ring", imageUrl = "https://s-media-cache-ak0.pinimg.com/236x/65/75/59/657559342e32d10975bf99961ab084f0--fellowship-of-the-ring-lord-of-the-rings.jpg"))
//        books.add(Book(id = "17", title = "The Two Towers", imageUrl = "https://img1.od-cdn.com/ImageType-400/0874-1/A1D/40F/DB/%7BA1D40FDB-55BF-4C91-BB96-3803FA25B575%7DImg400.jpg"))
//        books.add(Book(id = "18", title = "The Return of the King", imageUrl = "https://i.stack.imgur.com/071TF.jpg"))
//        books.add(Book(id = "19", title = "The Fellowship of the Ring", imageUrl = "https://s-media-cache-ak0.pinimg.com/236x/65/75/59/657559342e32d10975bf99961ab084f0--fellowship-of-the-ring-lord-of-the-rings.jpg"))
//        books.add(Book(id = "20", title = "The Two Towers", imageUrl = "https://img1.od-cdn.com/ImageType-400/0874-1/A1D/40F/DB/%7BA1D40FDB-55BF-4C91-BB96-3803FA25B575%7DImg400.jpg"))
//
//        return books
//    }
}
