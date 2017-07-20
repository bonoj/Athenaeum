package bonoj.me.athenaeum.books

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import bonoj.me.athenaeum.R
import bonoj.me.athenaeum.data.Book
import bonoj.me.athenaeum.data.BooksDataSource
import bonoj.me.athenaeum.root.AthenaeumApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_books.*
import javax.inject.Inject

class BooksActivity : AppCompatActivity(), BooksContract.View {

    @Inject
    lateinit var booksDataSource: BooksDataSource

    lateinit internal var presenter: BooksPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        AthenaeumApplication.graph.inject(this)

        presenter = BooksPresenter(this, booksDataSource, AndroidSchedulers.mainThread())
        presenter.loadBooks()
    }

    override fun onStop() {
        super.onStop()

        presenter.unsubscribe()
    }

    override fun displayBooks(books: List<Book>) {

        Log.i("MVP view", "total books: " + books.size.toString())

        var bookString: String = ""
        for (book in books) {
            bookString += book.title + "\n"
        }
        books_debug_tv.text = bookString
    }

    override fun displayNoBooks() {
        Log.i("MVP view", "no books")
    }

    override fun displayError() {
        Log.i("MVP view", "please check your connection")
    }
}
