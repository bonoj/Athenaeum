package bonoj.me.athenaeum.root;

import android.content.Context;

import javax.inject.Singleton;

import bonoj.me.athenaeum.data.BooksDataSource;
import bonoj.me.athenaeum.data.source.remote.BooksRemoteRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final AthenaeumApplication application;

    private BooksRemoteRepository booksRemoteRepository;

    public ApplicationModule(AthenaeumApplication application){
        this.application = application;

        booksRemoteRepository = new BooksRemoteRepository(application);
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    BooksDataSource provideConnectionsRepository(Context context) {
        //return new ConnectionsLocalRepository(context);
        return booksRemoteRepository;
    }
}
