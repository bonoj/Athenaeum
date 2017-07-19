package bonoj.me.athenaeum.root

import android.app.Application

class AthenaeumApplication : Application() {
    var applicationComponent: ApplicationComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()

        // needs to run once to generate it
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
