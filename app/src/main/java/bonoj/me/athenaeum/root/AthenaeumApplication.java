package bonoj.me.athenaeum.root;

import android.app.Application;

public class AthenaeumApplication extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        // needs to run once to generate it
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
