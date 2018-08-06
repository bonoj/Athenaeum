package bonoj.me.athenaeum.details

import bonoj.me.athenaeum.data.model.BookDetails
import bonoj.me.athenaeum.data.source.BooksDataSource
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(private val view: DetailsContract.View,
                       private val booksDataSource: BooksDataSource,
                       private val mainScheduler: Scheduler) : DetailsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun loadDetails() {

        val disposableSingleObserver = booksDataSource.getBookDetails(view.id)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(object : DisposableSingleObserver<BookDetails>() {
                    override fun onSuccess(bookDetails: BookDetails) {
                        view.displayDetails(bookDetails)
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

