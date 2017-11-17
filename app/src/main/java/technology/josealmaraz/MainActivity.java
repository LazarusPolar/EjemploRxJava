package technology.josealmaraz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
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

        //Version ReactiveX

        twitchAPI.getTopGamesObservable("q7ec8ghe4owvs1s4ssc33qx7jbzcot")
                .flatMap(new Func1<Twitch, Observable<Top>>() {
                    @Override
                    public Observable<Top> call(Twitch twitch) {
                        return Observable.from(twitch.getTop());
                    }
                }).flatMap(new Func1<Top, Observable<String>>() {
            @Override
            public Observable<String> call(Top top) {
                return Observable.just(top.getGame().getName());
            }
        })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.startsWith("H");
                    }
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("Desde RXJava:" + s);
            }
        });

        twitchAPI.getTopGamesObservable("q7ec8ghe4owvs1s4ssc33qx7jbzcot")
                .flatMap(new Func1<Twitch, Observable<Top>>() {
            @Override
            public Observable<Top> call(Twitch twitch) {
                return Observable.from(twitch.getTop());
            }
        }).flatMap(new Func1<Top, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Top top) {
                return Observable.just(top.getGame().getPopularity());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("La popularidad es: " + integer);
            }
        });

        }
    }

