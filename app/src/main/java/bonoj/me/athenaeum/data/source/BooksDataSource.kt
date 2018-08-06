package bonoj.me.athenaeum.data.source

import bonoj.me.athenaeum.data.model.Book
import bonoj.me.athenaeum.data.model.BookDetails
import io.reactivex.Single

interface BooksDataSource {

    val books: Single<List<Book>>

    fun getBookDetails(id: String): Single<BookDetails>

    fun requestDetailsFromApi(id: String): BookDetails
}
