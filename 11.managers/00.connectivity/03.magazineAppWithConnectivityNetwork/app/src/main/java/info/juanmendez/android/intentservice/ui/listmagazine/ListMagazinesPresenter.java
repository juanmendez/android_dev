package info.juanmendez.android.intentservice.ui.listmagazine;

import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.helper.MVPUtils;
import info.juanmendez.android.intentservice.helper.NetworkUtil;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.service.network.NetworkReceiver;
import info.juanmendez.android.intentservice.service.provider.MagazineLoader;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.service.proxy.MagazineListProxy;
import info.juanmendez.android.intentservice.ui.MagazineActivity;
import info.juanmendez.android.intentservice.ui.magazine.IMagazineView;

/**
 * Created by Juan on 8/19/2015.
 */
public class ListMagazinesPresenter implements IListMagazinesPresenter {

    Activity activity;
    MagazineAdapter adapter;
    MagazineLoader loader;

    @Inject
    protected Bus bus;

    @Inject
    protected DownloadProxy downloadProxy;

    @Inject
    NetworkReceiver networkReceiver;

    @Inject
    ArrayList<Magazine> magazines;

    IListMagazinesView view;


    public ListMagazinesPresenter( Activity activity ){
        this.activity = activity;
        adapter = new MagazineAdapter( activity, new ArrayList<Magazine>());
        view = MVPUtils.getView( activity, IListMagazinesView.class );
        view.setAdapter(adapter);
    }

    @Override
    public void resume() {
        bus.register(this);
        networkReceiver.register( this );
        prepLoader();
    }

    private void prepLoader(){
        Loader loader = activity.getLoaderManager().getLoader(1);

        if ( magazines.size() == 0 ) {
            getMagazines();
        } else {
            onLoadFinished( loader, magazines );
        }
    }

    @Override
    public void pause() {
        bus.unregister(this);
        networkReceiver.unregister();
    }

    @Override
    public void getMagazines() {

        if(NetworkUtil.isConnected(activity))
        {
            MagazineListProxy proxy = new MagazineListProxy();
            proxy.startService(activity, this);
        }
        else
        {
            activity.getLoaderManager().initLoader(1, null, this);
        }
    }

    @Override
    public void loadMagazine() {
        downloadProxy.startService(this);
    }

    @Override
    public void onNetworkStatus(Boolean connected, String type) {

        if( magazines.size() == 0 && !connected ){
            getMagazines();
        }

        view.onNetworkStatus(connected, type);
    }

    @Override
    public void onMagazineListResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            activity.getLoaderManager().initLoader(1, null, this);
        }
    }

    @Override
    public void onDownloadResult(int resultCode) {
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void magazineActionUpdate( Magazine magazine ){

        switch( magazine.getStatus() ){
            case MagazineStatus.PENDING:
                loadMagazine();
                break;

            case MagazineStatus.READ:
                Intent intent = new Intent( activity, MagazineActivity.class);
                activity.startActivity(intent);
                break;
        }
    }

    @Override
    public Loader<ArrayList<Magazine>> onCreateLoader(int id, Bundle args) {
        loader = new MagazineLoader( activity );
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Magazine>> loader, ArrayList<Magazine> data ) {

        if( magazines.size() == 0 )
        {
            magazines.addAll( data );
        }

        adapter.addAll( magazines );
        view.onMagazineList();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Magazine>> loader) {

    }
}