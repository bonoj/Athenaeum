package bonoj.me.athenaeum.books

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bonoj.me.athenaeum.R
import bonoj.me.athenaeum.data.BooksDataSource
import bonoj.me.athenaeum.root.AthenaeumApplication
import kotlinx.android.synthetic.main.activity_books.*
import javax.inject.Inject

class BooksActivity : AppCompatActivity() {

    @Inject
    lateinit var booksDataSource: BooksDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        AthenaeumApplication.graph.inject(this)

        books_debug_tv.text = "Goodbye, ButterKnife!"
    }


}
