package bonoj.me.athenaeum.root

import android.content.Context

import javax.inject.Singleton

import bonoj.me.athenaeum.data.BooksDataSource
import bonoj.me.athenaeum.data.source.remote.BooksRemoteRepository
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: AthenaeumApplication) {

    private val booksRemoteRepository: BooksRemoteRepository

    init {

        booksRemoteRepository = BooksRemoteRepository(application)
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideConnectionsRepository(context: Context): BooksDataSource {
        //return new ConnectionsLocalRepository(context);
        return booksRemoteRepository
    }
}
