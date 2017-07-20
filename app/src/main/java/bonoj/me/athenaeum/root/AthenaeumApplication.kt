package bonoj.me.athenaeum.root

import android.app.Application

class AthenaeumApplication : Application() {

//    var applicationComponent: ApplicationComponent? = null
//        private set

//    override fun onCreate() {
//        super.onCreate()
//
//        // needs to run once to generate it
//        applicationComponent = DaggerApplicationComponent.builder()
//                .applicationModule(ApplicationModule(this))
//                .build()
//    }

    companion object {
        // Allow access from java code
        @JvmStatic lateinit var graph: ApplicationComponent
    }

//    @Inject
//    lateinit var locationManager: LocationManager

    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        graph.inject(this)
    }
}
