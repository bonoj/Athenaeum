package bonoj.me.athenaeum.root

import android.app.Application

class AthenaeumApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        graph.inject(this)
    }
}
