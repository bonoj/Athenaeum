package bonoj.me.athenaeum.root

import android.app.Application
import com.facebook.stetho.Stetho



class AthenaeumApplication : Application() {

    companion object {
        // Allow access from java code
        @JvmStatic lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        graph.inject(this)

        Stetho.initializeWithDefaults(this)
    }
}
