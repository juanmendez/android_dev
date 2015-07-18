package info.juanmendez.android.customloader.service;


import android.content.Context;
import android.content.AsyncTaskLoader;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import info.juanmendez.android.customloader.model.GithubAction;
import info.juanmendez.android.customloader.model.Repo;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Juan on 7/13/2015.
 */
public class GithubLoader extends AsyncTaskLoader<ArrayList<Repo>>
{
    ArrayList<Repo> oldData = new ArrayList<Repo>();
    Bus bus;
    GithubService service;
    String query = "android";

    public GithubLoader(Context context, Bus _bus) {
        super(context);
        bus = _bus;
        service = getGithubService();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        bus.register(this);

        if( !oldData.isEmpty() )
        {
            super.deliverResult(oldData);
        }
        else
        {
            forceLoad();
        }

    }

    @Override
    public ArrayList<Repo> loadInBackground() {

        try{
            //Thread.sleep(2000); this is intentional, to see reaction when canceling, or reloading.

            //what a clean way to pass url params through retrofit
            Map<String, String> params = new HashMap<String, String>();
            params.put( "q", query);
            params.put( "sort", "stars" );
            params.put( "order", "desc" );
            return service.searchRepo( params ).getItems();
        }
        catch (Exception e )
        {
            e.printStackTrace();
            Logging.print(e.getMessage());
        }

        return null;
    }

    @Override
    public void deliverResult(ArrayList<Repo> repos) {

        oldData = repos;

        if( isStarted() )
        {
            super.deliverResult(repos);
        }
    }

    @Subscribe
    public void gitActionHandler( GithubAction action ){
        if( action.getAction().equals(GithubAction.GITHUB_RELOAD) ){

            if( !action.getQuery().isEmpty() )
            {
                query = action.getQuery();
            }

            onContentChanged();
        }
        else
        if( action.getAction().equals( GithubAction.GITHUB_CANCEL) ){
            cancelLoad();
        }
    }

    private GithubService getGithubService(){

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES )
                .registerTypeAdapter( Date.class, new DateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( "https://api.github.com")
                .setErrorHandler( new MyErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter( new GsonConverter(gson))
                .build();
        GithubService service = restAdapter.create(GithubService.class);

        return service;
    }
}
