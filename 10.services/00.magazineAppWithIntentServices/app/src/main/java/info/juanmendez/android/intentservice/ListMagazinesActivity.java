package info.juanmendez.android.intentservice;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.ObjectGraph;
import info.juanmendez.android.intentservice.helper.Logging;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.module.ActivityModule;
import info.juanmendez.android.intentservice.service.provider.MagazineLoader;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.service.proxy.MagazineListProxy;

/**
 * Created by Juan on 7/29/2015.
 */
public class ListMagazinesActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<Magazine>>, AdapterView.OnItemClickListener {
    ListView list;
    MagazineLoader loader;
    MagazineAdapter adapter;
    MagazineApp app;

    @Inject
    protected Bus bus;

    @Inject
    protected DownloadProxy receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        app = (MagazineApp)getApplication();
        ObjectGraph graph = app.getGraph().plus( new ActivityModule(this));
        graph.inject( this );

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
                        getLoaderManager().initLoader(1, null, ListMagazinesActivity.this);
                    }
                }
            });
        } else {
            getLoaderManager().initLoader(1, null, this);
        }
    }

    private void prepDagger(Boolean start){

        if( start ){
            bus.register(this);
        }
        else{
            bus.unregister(this);
        }
    }

    @Override
    public Loader<ArrayList<Magazine>> onCreateLoader(int id, Bundle args) {

        loader = new MagazineLoader(this );
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
    public void magazineActionUpdate( Magazine magazine ){

        switch( magazine.getStatus() ){
            case MagazineStatus.PENDING:

                receiver.startService( new DownloadProxy.UiCallback() {
                    @Override
                    public void onReceiveResult(int resultCode) {
                        adapter.notifyDataSetChanged();
                    }
                });
            break;

            case MagazineStatus.READ:
                Intent intent = new Intent( this, MagazineActivity.class);
                startActivity(intent);
            break;
        }
    }
}
