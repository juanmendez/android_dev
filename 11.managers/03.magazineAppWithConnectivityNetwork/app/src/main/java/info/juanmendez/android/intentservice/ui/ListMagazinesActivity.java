package info.juanmendez.android.intentservice.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.ObjectGraph;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.helper.NetworkUtil;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.module.ActivityModule;
import info.juanmendez.android.intentservice.service.network.NetworkReceiver;
import info.juanmendez.android.intentservice.service.network.NetworkUpdate;
import info.juanmendez.android.intentservice.service.provider.MagazineLoader;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.service.proxy.MagazineListProxy;

/**
 * Created by Juan on 7/29/2015.
 */
public class ListMagazinesActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<Magazine>>, NetworkUpdate, MagazineListProxy.UiCallBack {
    ListView list;
    MagazineLoader loader;
    MagazineAdapter adapter;
    MagazineApp app;
    Button noNetworkButton;

    @Inject
    protected Bus bus;

    @Inject
    protected DownloadProxy downloadProxy;

    @Inject
    NetworkReceiver networkReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        noNetworkButton = (Button) findViewById(R.id.noNetworkButton );
        noNetworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isConnected(ListMagazinesActivity.this)) {
                    updateMagazineList();
                }
            }
        });

        app = (MagazineApp)getApplication();
        ObjectGraph graph = app.getGraph().plus( new ActivityModule(this));
        graph.inject(this);

        prepListView();
    }

    @Override
    public void onResume(){
        super.onResume();
        bus.register(this);
        prepLoader();
        networkReceiver.register();
    }

    @Override
    public void onPause(){
        bus.unregister(this);
        networkReceiver.unregister();
        super.onPause();
    }

    private void prepListView(){
        list = (ListView) findViewById(R.id.list );
        adapter = new MagazineAdapter( this, new ArrayList<Magazine>());
        list.setAdapter(adapter);
    }

    private void prepLoader(){
        Loader loader = getLoaderManager().getLoader(1);

        if (loader == null) {
            updateMagazineList();
        } else {
            getLoaderManager().initLoader(1, null, this);
        }
    }

    private void updateMagazineList(){
        MagazineListProxy proxy = new MagazineListProxy();
        proxy.startService(this, this);
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

    @Subscribe
    public void magazineActionUpdate( Magazine magazine ){

        switch( magazine.getStatus() ){
            case MagazineStatus.PENDING:

                downloadProxy.startService(new DownloadProxy.UiCallback() {
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

    @Override
    public void onNetworkStatus(Boolean connected, String type) {

        if( connected ){

            if( noNetworkButton.getVisibility() == View.VISIBLE )
            {
                noNetworkButton.setText(getString(R.string.error_network_refresh));
            }
        }
        else{
            noNetworkButton.setText( getString(R.string.error_network));
            noNetworkButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onReceiveResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            noNetworkButton.setVisibility(View.GONE);
            getLoaderManager().initLoader(1, null, ListMagazinesActivity.this);
        }
    }
}