package bonoj.me.athenaeum.root

import bonoj.me.athenaeum.books.BooksActivity
import bonoj.me.athenaeum.details.DetailsActivity
import bonoj.me.athenaeum.details.DetailsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: AthenaeumApplication)

    fun inject(booksActivity: BooksActivity)

    fun inject(detailsActivity: DetailsActivity)

    fun inject(detailsViewModel: DetailsViewModel)
}
