package bonoj.me.athenaeum.books;

import java.util.List;

import bonoj.me.athenaeum.data.Book;

public interface BooksContract {

    interface View {

        void displayBooks(List<Book> books);

        void displayNoBooks();

        void displayError();
    }

    interface Presenter {

        //void setView(BooksContract.View view);

        void loadBooks();

        void unsubscribe();
    }
}
