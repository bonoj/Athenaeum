package bonoj.me.athenaeum.data.source.remote

import android.content.Context
import android.util.Log
import bonoj.me.athenaeum.data.Book
import bonoj.me.athenaeum.data.BooksDataSource
import io.reactivex.Single
import java.util.*

class BooksRemoteRepository(private val context: Context) : BooksDataSource {

    override val books: Single<List<Book>>
        get() {
            val books = generateDummyBookList()

            return Single.fromCallable {
                println("Thread db: " + Thread.currentThread().id)

                Log.i("MVP model", "getBooks returned " + books.size + " connections")

                books
            }
        }

    private fun generateDummyBookList(): List<Book> {
        val books = ArrayList<Book>()
        books.add(Book())
        books.add(Book())
        books.add(Book())

        return books
    }
}
