package bonoj.me.athenaeum.data

import io.reactivex.Single

interface BooksDataSource {

    val books: Single<List<Book>>

    val bookDetails: Single<BookDetails>
}
