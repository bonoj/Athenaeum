package bonoj.me.athenaeum.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import bonoj.me.athenaeum.R
import bonoj.me.athenaeum.books.BooksActivity
import bonoj.me.athenaeum.data.model.BookDetails
import bonoj.me.athenaeum.data.source.BooksDataSource
import bonoj.me.athenaeum.root.AthenaeumApplication
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), DetailsContract.View {

    @Inject
    lateinit var booksDataSource: BooksDataSource

    //lateinit var presenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        AthenaeumApplication.graph.inject(this)

//        presenter = DetailsPresenter(this, booksDataSource, AndroidSchedulers.mainThread())
//        presenter.loadDetails()

        // Refactored to use ViewModel with LiveData and Kotlin Coroutines rather than a presenter with RxJava
        val detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)

        detailsViewModel.getBookDetails(id).observe(this, Observer { bookDetails ->
            if (bookDetails != null) {
                displayDetails(bookDetails)
            }
        })

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (!isConnected) {
            displayError()
        }
    }

    override fun onStop() {
        super.onStop()

       // presenter.unsubscribe()
    }

    override val id: String
        get() = intent.getStringExtra(BooksActivity.DETAILS_INTENT_KEY)

    override fun displayDetails(bookDetails: BookDetails) {

        supportActionBar?.setTitle(bookDetails.title)

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.placeholder)

        Glide.with(this)
                .load(bookDetails.imageLinks.thumbnail)
                .apply(requestOptions)
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
        if (bookDetails.previewLink.isEmpty()) {
            details_link_button.visibility = View.GONE
        } else {
            details_link_button.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse(bookDetails.previewLink)
                startActivity(browserIntent)
            }
        }

        details_scroll_view.visibility = View.VISIBLE
    }

    override fun displayError() {

        details_empty_tv.setText(R.string.connection_error)
        details_scroll_view.visibility = View.GONE
        details_empty_tv.visibility = View.VISIBLE

    }
}
