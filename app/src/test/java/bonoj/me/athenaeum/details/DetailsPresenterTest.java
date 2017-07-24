package bonoj.me.athenaeum.details;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;

import bonoj.me.athenaeum.data.BookDetails;
import bonoj.me.athenaeum.data.BooksDataSource;
import bonoj.me.athenaeum.data.model.ImageLinks;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class DetailsPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    BooksDataSource booksDataSource;

    @Mock
    DetailsContract.View view;

    private DetailsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new DetailsPresenter(
                view, booksDataSource, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void shouldDeliverBookDetailsToView() {

        BookDetails bookDetails = new BookDetails(
                "",
                "",
                Collections.emptyList(),
                "",
                "",
                "",
                Collections.emptyList(),
                0,
                "",
                Collections.emptyList(),
                0.0,
                0,
                "",
                new ImageLinks("", ""),
                "",
                "");

        Mockito.when(booksDataSource.getBookDetails()).thenReturn(Single.just(bookDetails));

        presenter.loadDetails();

        Mockito.verify(view).displayDetails(bookDetails);
    }

    @Test
    public void shouldHandleError() {

        Mockito.when(booksDataSource.getBookDetails()).thenReturn(Single.error(new Throwable("error")));

        presenter.loadDetails();

        Mockito.verify(view).displayError();
    }
}
