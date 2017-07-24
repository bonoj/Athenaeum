package bonoj.me.athenaeum.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

        var authorString: String = ""
        var i = 1
        for (author in bookDetails.authors) {
            authorString += author
            if (i < bookDetails.authors.size) {
                authorString += "\n"
            }
            i++
        }
        details_author_tv.setText(authorString)

        val publisherString = bookDetails.publisher + "\n" + bookDetails.publishedDate
        details_publisher_tv.setText(publisherString)

        val pages = bookDetails.pageCount
        if (pages == 0) {
            details_pages_tv.visibility = View.GONE
        } else {
            val pageString = pages.toString() + " " + getString(R.string.pages)
            details_pages_tv.setText(pageString)
        }

        if (bookDetails.averageRating == 0.0) {
            details_ratings_tv.visibility = View.GONE
        } else {
            val ratings =
                    getString(R.string.rated) + " " +
                            bookDetails.averageRating + " " +
                            getString(R.string.by) + " " +
                            bookDetails.ratingsCount + " " + getString(R.string.readers)
            details_ratings_tv.setText(ratings)
        }

        val description = bookDetails.description
        if (description == "") {
            details_description_tv.visibility = View.GONE
        } else {
            details_description_tv.setText(description)
        }

        var categories = bookDetails.categories.toString()
        if (categories.length < 3) {
            details_categories_tv.visibility = View.GONE
        } else {
            categories = categories.substring(1, categories.length - 2)
            details_categories_tv.setText(categories)
        }

        details_scroll_view.visibility = View.VISIBLE
    }

    override fun displayError() {
        // TODO Make pretty errors
        Log.i("MVP view", "please check your connection")
    }
}
