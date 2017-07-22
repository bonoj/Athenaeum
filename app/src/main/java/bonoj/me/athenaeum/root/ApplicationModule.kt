package bonoj.me.athenaeum.root

import android.content.Context
import bonoj.me.athenaeum.data.BooksDataSource
import bonoj.me.athenaeum.data.source.remote.BooksRemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AthenaeumApplication) {

    private val booksRemoteRepository: BooksRemoteRepository

    init {
        booksRemoteRepository = BooksRemoteRepository(application)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideBooksRemoteRepository(context: Context): BooksDataSource {
        return booksRemoteRepository
    }
}
