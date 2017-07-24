package bonoj.me.athenaeum.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import bonoj.me.athenaeum.R
import bonoj.me.athenaeum.books.BooksActivity
import bonoj.me.athenaeum.data.BookDetails
import bonoj.me.athenaeum.data.BooksDataSource
import bonoj.me.athenaeum.root.AthenaeumApplication
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), DetailsContract.View {

    @Inject
    lateinit var booksDataSource: BooksDataSource

    lateinit internal var presenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        AthenaeumApplication.graph.inject(this)

        presenter = DetailsPresenter(this, booksDataSource, AndroidSchedulers.mainThread())
        presenter.loadDetails()
    }

    override fun onStop() {
        super.onStop()

        presenter.unsubscribe()
    }

    override val id: String
        get() = intent.getStringExtra(BooksActivity.DETAILS_INTENT_KEY)

    override fun displayDetails(bookDetails: BookDetails) {

        supportActionBar?.setTitle(bookDetails.title)

        Glide.with(this)
                .load(bookDetails.imageLinks.thumbnail)
                .placeholder(R.drawable.placeholder)
                .into(details_cover_iv)

        details_title_tv.setText(bookDetails.title)
        details_publisher_tv.setText(bookDetails.publisherString)
        details_author_tv.setText(bookDetails.authorString)
        details_pages_tv.setText(bookDetails.pageString)
        details_ratings_tv.setText(bookDetails.ratingsString)
        if (bookDetails.description.length < 3) {
            details_description_tv.visibility = View.GONE
        } else {
            details_description_tv.setText(bookDetails.description)
        }
        if (bookDetails.categoriesString.length < 3) {
            details_categories_tv.visibility = View.GONE
        } else {
            details_categories_tv.setText(bookDetails.categoriesString)
        }

        details_scroll_view.visibility = View.VISIBLE
    }

    override fun displayError() {

        details_empty_tv.setText(R.string.connection_error)
        details_scroll_view.visibility = View.GONE
        details_empty_tv.visibility = View.VISIBLE

    }
}
