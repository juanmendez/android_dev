package info.juanmendez.android.intentservice.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import info.juanmendez.android.intentservice.service.versioning.RetrofitErrorHandler;
import info.juanmendez.android.intentservice.service.versioning.RetroService;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Juan on 7/22/2015.
 */
public class MagazineService
{
    public static String localhost(){
        return "http://192.168.45.1";
    }

    public static RetroService getService(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( localhost() )
                .setErrorHandler( new RetrofitErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();

        return restAdapter.create(RetroService.class);
    }

    static class MyErrorHandler implements ErrorHandler {
        @Override
        public Throwable handleError(RetrofitError cause) {
            Response r = cause.getResponse();
            if (r != null && r.getStatus() == 401) {
                return new Exception("Unathorized cause");
            }
            return cause;
        }
    }
}
