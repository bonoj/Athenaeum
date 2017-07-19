package bonoj.me.athenaeum.root

import bonoj.me.athenaeum.books.BooksActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: AthenaeumApplication)

    fun inject(booksActivity: BooksActivity)

    //void inject(DetailsActivity detailsActivity);
}
