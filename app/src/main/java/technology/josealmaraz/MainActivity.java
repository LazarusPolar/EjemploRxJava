package technology.josealmaraz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technology.josealmaraz.api.Top;
import technology.josealmaraz.api.Twitch;
import technology.josealmaraz.api.TwitchAPI;
import technology.josealmaraz.retrofitejemplo.R;
import technology.josealmaraz.root.App;

public class MainActivity extends AppCompatActivity {

    @Inject
    TwitchAPI twitchAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getComponent().inject(this);
        Call<Twitch> call = twitchAPI.getTopGames("q7ec8ghe4owvs1s4ssc33qx7jbzcot");

        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Top> gameList = response.body().getTop();

                for(Top top: gameList){
                    System.out.println(top.getGame().getName());
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
