package technology.josealmaraz.api;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Usuario on 17/11/2017.
 */

@Module
public class ApiModule {

    String BASE_URL = "https://api.twitch.tv/kraken/";

    @Provides
    public OkHttpClient providesHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    public Retrofit providesRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public TwitchAPI providesApiService() {
        return providesRetrofit(BASE_URL, providesHttpClient()).create(TwitchAPI.class);
    }
}
