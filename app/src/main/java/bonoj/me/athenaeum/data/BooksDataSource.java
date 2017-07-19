package bonoj.me.athenaeum.data;

import java.util.List;

import io.reactivex.Single;

public interface BooksDataSource {

    Single<List<Book>> getBooks();

//    int insertNewBook(Book newBook);
//
//    int deleteBook(int databaseId);
}
