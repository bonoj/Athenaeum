package bonoj.me.athenaeum.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
        //presenter.loadDetails(intent.getStringExtra(BooksActivity.DETAILS_INTENT_KEY))
        presenter.loadDetails()
    }

    override fun onStop() {
        super.onStop()

        presenter.unsubscribe()
    }

    override val id: String
        get() = intent.getStringExtra(BooksActivity.DETAILS_INTENT_KEY)

    override fun displayDetails(bookDetails: BookDetails) {

        Log.i("DETAILS VIEW", bookDetails.toString())

        Glide.with(this)
                .load(bookDetails.imageLinks.thumbnail)
                .placeholder(R.drawable.placeholder)
                .into(details_cover_iv)
    }

    override fun displayError() {
        Log.i("MVP view", "please check your connection")
    }
}
