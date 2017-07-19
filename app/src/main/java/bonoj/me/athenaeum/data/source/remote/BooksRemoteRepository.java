package bonoj.me.athenaeum.data.source.remote;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bonoj.me.athenaeum.data.Book;
import bonoj.me.athenaeum.data.BooksDataSource;
import io.reactivex.Single;

public class BooksRemoteRepository implements BooksDataSource {

    @Override
    public Single<List<Book>> getBooks() {
        List<Book> books = generateDummyBookList();

        return Single.fromCallable(new Callable<List<Book>>() {
            @Override
            public List<Book> call() throws Exception {

                System.out.println("Thread db: " + Thread.currentThread().getId());

                Log.i("MVP model", "getBooks returned " + books.size() + " connections");

                return books;
            }
        });
    }

    private List<Book> generateDummyBookList() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());

        return books;
    }
}
