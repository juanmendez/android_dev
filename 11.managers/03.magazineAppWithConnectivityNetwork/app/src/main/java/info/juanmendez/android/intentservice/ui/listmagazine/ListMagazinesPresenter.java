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

import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.service.network.NetworkReceiver;
import info.juanmendez.android.intentservice.service.provider.MagazineLoader;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.service.proxy.MagazineListProxy;
import info.juanmendez.android.intentservice.ui.MagazineActivity;

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


    public ListMagazinesPresenter( Activity activity ){
        this.activity = activity;
        adapter = new MagazineAdapter( activity, new ArrayList<Magazine>());
        getView().setAdapter(adapter);
    }


    IListMagazinesView getView(){

        try{
            return (IListMagazinesView) activity;
        }catch( ClassCastException e ){

            throw new IllegalStateException( activity.getClass().getSimpleName() +
                    " does not implement contract interface" +
                    getClass().getSimpleName(), e );
        }
    }

    @Override
    public void resume() {
        bus.register(this);
        networkReceiver.register( this );
        prepLoader();
    }

    private void prepLoader(){
        Loader loader = activity.getLoaderManager().getLoader(1);

        if (loader == null && magazines.size() == 0 ) {
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

        MagazineListProxy proxy = new MagazineListProxy();
        proxy.startService((Activity) activity, this);
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

        getView().onNetworkStatus(connected, type);
    }

    @Override
    public void onMagazineListResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            activity.getLoaderManager().initLoader(1, null, this);
            getView().onMagazineList();
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

        adapter.addAll( magazines );
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Magazine>> loader) {

    }
}