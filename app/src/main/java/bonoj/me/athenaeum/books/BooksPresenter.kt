package bonoj.me.athenaeum.books

import bonoj.me.athenaeum.data.Book
import bonoj.me.athenaeum.data.BooksDataSource
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class BooksPresenter(private val view: BooksContract.View,
                     private val booksDataSource: BooksDataSource,
                     private val mainScheduler: Scheduler) : BooksContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun loadBooks() {

        val disposableSingleObserver = booksDataSource.books
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(object : DisposableSingleObserver<List<Book>>() {
                    override fun onSuccess(books: List<Book>) {

                        if (books.isEmpty()) {
                            view.displayNoBooks()
                        } else {
                            view.displayBooks(books)
                        }
                    }

                    override fun onError(e: Throwable) {
                        view.displayError()
                    }
                })

        compositeDisposable.add(disposableSingleObserver)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}
