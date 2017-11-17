package technology.josealmaraz.api;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.plugins.RxAndroidPlugins;

/**
 * Created by Usuario on 17/11/2017.
 */

@Module
public class ApiModule {

    String BASE_URL = "https://api.twitch.tv/kraken/";

    @Provides
    public OkHttpClient providesHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    public Retrofit providesRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                //Para que RetroFit sepa que es un Observable
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    public TwitchAPI providesApiService() {
        return providesRetrofit(BASE_URL, providesHttpClient()).create(TwitchAPI.class);
    }
}
