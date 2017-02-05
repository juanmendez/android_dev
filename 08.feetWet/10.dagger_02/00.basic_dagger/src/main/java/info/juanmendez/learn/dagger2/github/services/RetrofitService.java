package info.juanmendez.learn.dagger2.github.services;

import info.juanmendez.learn.dagger2.github.models.GitConstants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by musta on 2/3/2017.
 */
public class RetrofitService {
    String githubApi;

    public RetrofitService(String api ){
        this.githubApi = api;
    }

    public Retrofit getRetrofit(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(this.githubApi)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        return builder.client(httpClient.build()).build();
    }
}
