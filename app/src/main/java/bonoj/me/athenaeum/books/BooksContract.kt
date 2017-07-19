package bonoj.me.athenaeum.books

import bonoj.me.athenaeum.data.Book

interface BooksContract {

    interface View {

        fun displayBooks(books: List<Book>)

        fun displayNoBooks()

        fun displayError()
    }

    interface Presenter {

        //void setView(BooksContract.View view);

        fun loadBooks()

        fun unsubscribe()
    }
}
