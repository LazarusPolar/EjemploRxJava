package technology.josealmaraz.root;

import dagger.Component;
import technology.josealmaraz.MainActivity;
import technology.josealmaraz.api.ApiModule;

/**
 * Created by Usuario on 17/11/2017.
 */

@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    void inject(MainActivity target);
}
