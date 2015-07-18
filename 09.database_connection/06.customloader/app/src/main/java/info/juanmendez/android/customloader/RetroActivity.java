package info.juanmendez.android.customloader;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if( Intent.ACTION_SEARCH.equals( intent.getAction() )){
            createQueryAction( intent.getStringExtra(SearchManager.QUERY) );
        }
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
        else
        if( id == R.id.action_search){
            Logging.print( "action search");
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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( action.getQuery() );
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Repo>> loader) {
        listView.setAdapter( new RepoAdapter(this, new ArrayList<Repo>() ));
    }

    private void createAction( String type ){
        action = new GithubAction( type );
        ((App) getApplication()).getBus().post( action );
    }

    private void createQueryAction( String query ){
        action = new GithubAction( GithubAction.GITHUB_RELOAD );
        action.setQuery( query);
        ((App) getApplication()).getBus().post( action );
    }

    @Produce
    public GithubAction setGithubAction(){
        return action;
    }
}
