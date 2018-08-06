package bonoj.me.athenaeum.root

import android.content.Context
import bonoj.me.athenaeum.data.source.BooksDataSource
import bonoj.me.athenaeum.data.source.BooksRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AthenaeumApplication) {

    private val booksRepository: BooksRepository

    init {
        booksRepository = BooksRepository(application)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideBooksRemoteRepository(context: Context): BooksDataSource {
        return booksRepository
    }
}
