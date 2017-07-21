package bonoj.me.athenaeum.books

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import bonoj.me.athenaeum.R
import bonoj.me.athenaeum.data.Book
import bonoj.me.athenaeum.data.BooksDataSource
import bonoj.me.athenaeum.details.DetailsActivity
import bonoj.me.athenaeum.root.AthenaeumApplication
import com.facebook.stetho.Stetho
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_books.*
import javax.inject.Inject

class BooksActivity : AppCompatActivity(), BooksContract.View, BooksAdapter.ItemClickListener {

    @Inject
    lateinit var booksDataSource: BooksDataSource

    lateinit internal var presenter: BooksPresenter
    lateinit internal var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        AthenaeumApplication.graph.inject(this)

        Stetho.initializeWithDefaults(this)

        val columns: Int = resources.getInteger(R.integer.columns)
        val layoutManager: GridLayoutManager = GridLayoutManager(this, columns)

        adapter = BooksAdapter(this, this)
        books_list_rv.setHasFixedSize(true)
        books_list_rv.setLayoutManager(layoutManager)
        books_list_rv.adapter = adapter

        presenter = BooksPresenter(this, booksDataSource, AndroidSchedulers.mainThread())
        presenter.loadBooks()
    }

    override fun onStop() {
        super.onStop()

        presenter.unsubscribe()
    }

    override fun displayBooks(books: List<Book>) {

        Log.i("MVP view", "displayBooks has " + books.size.toString() + " books")

        var bookString: String = ""
        for (book in books) {
            bookString += book.title + "\n"
        }
        books_debug_tv.text = bookString

        adapter.setBooks(books)

        books_debug_tv.visibility = View.GONE
        books_list_rv.visibility = View.VISIBLE
    }

    override fun displayNoBooks() {
        Log.i("MVP view", "no books")

        books_list_rv.visibility = View.GONE
        books_debug_tv.visibility = View.VISIBLE
    }

    override fun displayError() {
        Log.i("MVP view", "please check your connection")

        books_list_rv.visibility = View.GONE
        books_debug_tv.visibility = View.VISIBLE
    }

    override fun onItemClick(view: View, position: Int) {
        Log.i("MVP view", "clicked position " + position.toString())

        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }
}
