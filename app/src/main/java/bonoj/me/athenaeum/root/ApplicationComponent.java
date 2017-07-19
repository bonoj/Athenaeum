package bonoj.me.athenaeum.root;

import javax.inject.Singleton;

import bonoj.me.athenaeum.books.BooksActivity;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(AthenaeumApplication application);

    void inject(BooksActivity booksActivity);

    //void inject(DetailsActivity detailsActivity);
}
