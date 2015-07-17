package info.juanmendez.android.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.List;

import info.juanmendez.android.retrofitdemo.github.GithubService;
import info.juanmendez.android.retrofitdemo.github.MyErrorHandler;
import info.juanmendez.android.retrofitdemo.github.Repo;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class RetroActivity extends AppCompatActivity {

    public static final String TAG = "RetrofitDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);
        GithubService service = getGithubService();

        try{
            service.listRepos("juanmendez", new Callback<List<Repo>>() {
                @Override
                public void success(List<Repo> repos, Response response) {
                    for( Repo repo: repos ){
                        Log.e( TAG, repo.getFullName() + "," + repo.getCreatedAt() );
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    Log.e( TAG, "retrofit error: "  + error.getMessage() );
                }
            });
        }
        catch (Exception e )
        {
            e.printStackTrace();
            Log.e( TAG, e.getMessage() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_retro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
