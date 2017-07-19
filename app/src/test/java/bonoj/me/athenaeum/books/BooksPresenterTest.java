package bonoj.me.athenaeum.books;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bonoj.me.athenaeum.data.Book;
import bonoj.me.athenaeum.data.BooksDataSource;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class BooksPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    BooksDataSource booksDataSource;

    @Mock
    BooksContract.View view;

    private BooksPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new BooksPresenter(
                view, booksDataSource, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void shouldDeliverConnectionsToView() {

        List<Book> books = Arrays.asList(
                new Book(),
                new Book(),
                new Book());
        Mockito.when(booksDataSource.getBooks()).thenReturn(Single.just(books));

        presenter.loadBooks();

        Mockito.verify(view).displayBooks(books);
    }

    @Test
    public void shouldHandleNoConnectionsFound() {

        List<Book> connections = Collections.emptyList();
        Mockito.when(booksDataSource.getBooks()).thenReturn(Single.just(connections));

        presenter.loadBooks();

        Mockito.verify(view).displayNoBooks();
    }

    @Test
    public void shouldHandleError() {

        Mockito.when(booksDataSource.getBooks()).thenReturn(Single.<List<Book>>error(new Throwable("error")));

        presenter.loadBooks();

        Mockito.verify(view).displayError();
    }
}
