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
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_books.*
import javax.inject.Inject

class BooksActivity : AppCompatActivity(), BooksContract.View, BooksAdapter.ItemClickListener {

    companion object {
        @JvmStatic
        val DETAILS_INTENT_KEY = "bonoj.me.athenaeum.DETAILS_INTENT_KEY"
    }

    private val BOOKS_PARCEL_KEY = "bonoj.me.athenaeum.BOOKS_PARCEL_KEY"

    @Inject
    lateinit var booksDataSource: BooksDataSource

    lateinit internal var presenter: BooksPresenter
    lateinit internal var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        AthenaeumApplication.graph.inject(this)

        val columns = resources.getInteger(R.integer.columns)
        val layoutManager = GridLayoutManager(this, columns)

        adapter = BooksAdapter(this, this)
        books_list_rv.setHasFixedSize(true)
        books_list_rv.setLayoutManager(layoutManager)
        books_list_rv.adapter = adapter
        books_list_rv.addOnScrollListener(
                EndlessScrollListener({ presenter.loadBooks() }, layoutManager)
        )

        if (savedInstanceState != null) {
            adapter.refillAdapterAfterDeviceRotation(savedInstanceState.getParcelableArrayList(BOOKS_PARCEL_KEY))
        }

        presenter = BooksPresenter(this, booksDataSource, AndroidSchedulers.mainThread())
        presenter.loadBooks()
    }

    override fun onStop() {
        super.onStop()

        presenter.unsubscribe()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putParcelableArrayList(BOOKS_PARCEL_KEY, adapter.getBooksParcel())
    }

    override fun displayBooks(books: List<Book>) {

        adapter.setBooks(books)

        books_empy_tv.visibility = View.GONE
        books_list_rv.visibility = View.VISIBLE
    }

    override fun displayNoBooks() {
        Log.i("MVP view", "no books")

        if (books_list_rv.adapter.itemCount == 0) {
            books_list_rv.visibility = View.GONE
            books_empy_tv.visibility = View.VISIBLE
        }
    }

    override fun displayError() {
        Log.i("MVP view", "please check your connection")

        if (books_list_rv.adapter.itemCount == 0) {
            books_list_rv.visibility = View.GONE
            books_empy_tv.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(view: View, position: Int) {
        Log.i("MVP view", "tag " + view.getTag())

        val id: String? = view.getTag() as String?

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DETAILS_INTENT_KEY, id)
        startActivity(intent)
    }
}
