package bonoj.me.athenaeum.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import bonoj.me.athenaeum.data.model.BookDetails
import bonoj.me.athenaeum.data.source.BooksDataSource
import bonoj.me.athenaeum.root.AthenaeumApplication
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class DetailsViewModel : ViewModel() {

    @Inject
    lateinit var booksDataSource: BooksDataSource

    init {
        AthenaeumApplication.graph.inject(this)
    }

    private var bookDetails: MutableLiveData<BookDetails>? = null
    fun getBookDetails(id: String): LiveData<BookDetails> {
        if (bookDetails == null) {
            bookDetails = MutableLiveData()
            launch {
                val job = loadBookDetails(id)
                bookDetails?.postValue(job.await())
            }
        }
        return bookDetails!!
    }

    private fun loadBookDetails(id: String): Deferred<BookDetails> {
        return async {
            booksDataSource.requestDetailsFromApi(id)
        }
    }
}