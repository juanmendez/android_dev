package info.juanmendez.android.customloader;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.Date;

import info.juanmendez.android.customloader.model.Repo;
import info.juanmendez.android.customloader.service.GithubService;
import info.juanmendez.android.customloader.service.MyErrorHandler;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Juan on 7/13/2015.
 */
public class App extends Application
{
    private final Bus bus = new Bus(ThreadEnforcer.ANY);

    public void setList(ArrayList<Repo> list) {
        this.list = list;
    }

    public ArrayList<Repo> getList() {
        return list;
    }


    public Repo getRepo( int id ){

        for( Repo repo: list ){
            if( repo.equals(id)){
                return repo;
            }
        }

        return null;
    }

    private ArrayList<Repo> list = new ArrayList<Repo>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Bus getBus(){
        return bus;
    }


    private GithubService  gitService;

    public GithubService getGithubService(){


        if( gitService == null )
        {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint( "https://api.github.com")
                    .setErrorHandler( new MyErrorHandler())
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter( new GsonConverter(gson))
                    .build();
            gitService = restAdapter.create(GithubService.class);
        }


        return gitService;
    }
}
