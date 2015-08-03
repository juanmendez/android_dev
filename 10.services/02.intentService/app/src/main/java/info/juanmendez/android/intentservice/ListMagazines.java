package info.juanmendez.android.intentservice;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.helper.Logging;
import info.juanmendez.android.intentservice.helper.MagazineListProxy;
import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.model.MagazineAction;
import info.juanmendez.android.intentservice.model.MagazineAdapter;
import info.juanmendez.android.intentservice.service.provider.MagazineLoader;

/**
 * Created by Juan on 7/29/2015.
 */
public class ListMagazines extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<Magazine>>, AdapterView.OnItemClickListener {
    ListView list;
    MagazineLoader loader;
    MagazineAdapter adapter;

    @Inject
    Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        prepListView();
        prepLoader();
    }

    @Override
    public void onResume(){
        super.onResume();
        prepLoader();
        prepDagger(true);
    }

    @Override
    public void onPause(){
        super.onPause();
        prepDagger(false);
    }

    private void prepListView(){
        list = (ListView) findViewById(R.id.list );
        adapter = new MagazineAdapter( this, new ArrayList<Magazine>());
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    private void prepLoader(){
        Loader loader = getLoaderManager().getLoader(1);

        if (loader == null) {
            MagazineListProxy proxy = new MagazineListProxy();
            proxy.startService(this, new MagazineListProxy.UiCallBack() {
                @Override
                public void onReceiveResult(int resultCode) {
                    if (resultCode == Activity.RESULT_OK) {
                        getLoaderManager().initLoader(1, null, ListMagazines.this);
                    }
                }
            });
        } else {
            getLoaderManager().initLoader(1, null, this);
        }
    }

    private void prepDagger(Boolean start){
        MagazineApp app = (MagazineApp)getApplication();

        if( start ){
            app.inject( this );
            bus.register(this);
        }
        else{
            bus.unregister(this);
        }
    }

    @Override
    public Loader<ArrayList<Magazine>> onCreateLoader(int id, Bundle args) {
        loader = new MagazineLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Magazine>> loader, ArrayList<Magazine> magazines) {

        adapter.addAll( magazines );
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Magazine>> loader) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Logging.print( "clicked item at " + adapter.getItem(position));
    }

    @Subscribe
    public void magazineActionUpdate( MagazineAction action ){
        Logging.print( "new action " + action.getAction() );
    }
}
