package info.juanmendez.android.customloader;


import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.squareup.otto.Produce;

import java.util.ArrayList;

import info.juanmendez.android.customloader.model.GithubAction;
import info.juanmendez.android.customloader.model.Repo;
import info.juanmendez.android.customloader.service.GithubLoader;
import info.juanmendez.android.customloader.service.Logging;
import info.juanmendez.android.customloader.service.RepoAdapter;

public class RetroActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Repo>> {

    private GithubLoader loader;
    private GithubAction action = new GithubAction("");
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);
        listView = (ListView) findViewById(R.id.listView);
        this.getLoaderManager().initLoader(5, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ((App) getApplication()).getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((App) getApplication()).getBus().unregister(this);
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
        if (id == R.id.restart_loader) {

            createAction(GithubAction.GITHUB_RELOAD);
            return true;
        }
        else
        if( id == R.id.cancel_loader ){
            createAction(GithubAction.GITHUB_CANCEL);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<Repo>> onCreateLoader(int id, Bundle args) {

        loader = new GithubLoader(this, ((App) getApplication()).getBus() );
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Repo>> loader, ArrayList<Repo> list) {
        listView.setAdapter( new RepoAdapter(this, list) );
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Repo>> loader) {

    }

    private void createAction( String type ){
        action = new GithubAction( type );
        ((App) getApplication()).getBus().post( action );
    }

    @Produce
    public GithubAction setGithubAction(){
        return action;
    }
}
