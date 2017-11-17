package technology.josealmaraz.api;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import rx.*;

/**
 * Created by Usuario on 17/11/2017.
 */

public interface TwitchAPI {

    @GET("games/top")
    Call<Twitch> getTopGames(@Header("Client-Id") String clientId);

    @GET("games/top")
    rx.Observable<Twitch> getTopGamesObservable(@Header("Client-Id") String clientId);
}
