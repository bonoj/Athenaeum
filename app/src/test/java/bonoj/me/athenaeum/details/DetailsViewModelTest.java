package bonoj.me.athenaeum.details;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import bonoj.me.athenaeum.data.model.BookDetails;
import bonoj.me.athenaeum.data.model.ImageLinks;
import bonoj.me.athenaeum.data.source.BooksDataSource;

public class DetailsViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    BooksDataSource booksDataSource;

    DetailsViewModel detailsViewModel;


    @Before
    public void setUp() throws Exception {

      //  detailsViewModel = new DetailsViewModel();




    }

    @Test
    public void shouldDeliverBookDetailsToView() {

        BookDetails bookDetails = new BookDetails(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                new ImageLinks("", ""),
                "");




//        Mockito.when(view.getId()).thenReturn("");
//        Mockito.when(booksDataSource.getBookDetails("")).thenReturn(Single.just(bookDetails));
//
//        presenter.loadDetails();
//
//        Mockito.verify(view).displayDetails(bookDetails);
    }
}
