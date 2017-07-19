package bonoj.me.athenaeum.books;

import android.support.annotation.NonNull;

import java.util.List;

import bonoj.me.athenaeum.data.Book;
import bonoj.me.athenaeum.data.BooksDataSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BooksPresenter implements BooksContract.Presenter {

    private BooksContract.View view;
    private BooksDataSource booksDataSource;
    private Scheduler mainScheduler;

    // Create a composite for RxJava subscriber cleanup
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BooksPresenter(BooksContract.View view,
                          BooksDataSource booksDataSource,
                          Scheduler mainScheduler) {

        this.view = view;
        this.booksDataSource = booksDataSource;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void loadBooks() {

        DisposableSingleObserver<List<Book>> disposableSingleObserver =
                booksDataSource.getBooks()
                        .subscribeOn(Schedulers.io())
                        .observeOn(mainScheduler)
                        .subscribeWith(new DisposableSingleObserver<List<Book>>() {
                            @Override
                            public void onSuccess(@NonNull List<Book> books) {

                                System.out.println("Thread subscribe: " + Thread.currentThread().getId());

                                if (books.isEmpty()) {
                                    view.displayNoBooks();
                                } else {
                                    view.displayBooks(books);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                view.displayError();
                            }
                        });

        // Add this subscription to the RxJava cleanup composite
        compositeDisposable.add(disposableSingleObserver);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
