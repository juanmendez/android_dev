package info.juanmendez.learn.dagger2.github.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by musta on 2/3/2017.
 */
public class RetrofitService {

    public Retrofit createRetrofit(){
        String API_BASE_URL = "https://api.module.com/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        return builder.client(httpClient.build()).build();
    }
}
