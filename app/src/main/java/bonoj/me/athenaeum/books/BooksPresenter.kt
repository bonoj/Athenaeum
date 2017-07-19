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

    // Create a composite for RxJava subscriber cleanup
    private val compositeDisposable = CompositeDisposable()

    override fun loadBooks() {

        val disposableSingleObserver = booksDataSource.books
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(object : DisposableSingleObserver<List<Book>>() {
                    override fun onSuccess(books: List<Book>) {

                        println("Thread subscribe: " + Thread.currentThread().id)

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

        // Add this subscription to the RxJava cleanup composite
        compositeDisposable.add(disposableSingleObserver)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}
