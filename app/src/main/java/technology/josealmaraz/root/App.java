package technology.josealmaraz.root;

import android.app.Application;

/**
 * Created by Usuario on 17/11/2017.
 */

public class App extends Application{

    AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
