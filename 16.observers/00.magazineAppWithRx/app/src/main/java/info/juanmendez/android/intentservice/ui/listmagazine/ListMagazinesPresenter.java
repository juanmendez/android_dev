package info.juanmendez.android.intentservice.ui.listmagazine;

import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.helper.MVPUtils;
import info.juanmendez.android.intentservice.helper.NetworkUtil;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.model.pojo.Log;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.MagazineNotificationSubject;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;
import info.juanmendez.android.intentservice.service.network.NetworkReceiver;
import info.juanmendez.android.intentservice.service.provider.MagazineLoader;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.ui.MagazineActivity;
import rx.Subscription;

/**
 * Created by Juan on 8/19/2015.
 *
 * This presenter takes care to feed a list adapter
 * and commands other services to pull new magazines, store them on the db
 * and finally query to fill the adapter and indirectly feed the activity's list view
 */
public class ListMagazinesPresenter implements IListMagazinesPresenter {

    Activity activity;
    MagazineAdapter adapter;
    MagazineLoader loader;

    //@Inject
    //protected Bus bus;

    //pull new magazines from restful service
    //and populate db
    @Inject
    protected DownloadProxy downloadProxy;

    //detect connection and notify this presenter
    @Inject
    NetworkReceiver networkReceiver;

    @Inject
    Log log;

    @Inject
    ArrayList<Magazine> magazines;

    @Inject
    MagazineDispatcher magazineDispatcher;

    @Inject
    MagazineNotificationSubject magazineNotificationSubject;

    Subscription magazineSubscription;
    Subscription notificationSubscription;

    IListMagazinesView view;

    public ListMagazinesPresenter( Activity activity ){
        this.activity = activity;
        adapter = new MagazineAdapter( activity.getApplication(), new ArrayList<Magazine>());
        view = MVPUtils.getView( activity, IListMagazinesView.class );
        view.setAdapter(adapter);
    }

    @Override
    public void resume() {

        magazineSubscription = magazineDispatcher.subscribe( magazine -> {
            switch( magazine.getStatus() ){
                case MagazineStatus.PENDING:
                    loadMagazine();
                    break;

                case MagazineStatus.READ:
                    Intent intent = new Intent( activity, MagazineActivity.class);
                    activity.startActivity(intent);
                    break;
            }
        });

        notificationSubscription = magazineNotificationSubject.subscribe(magazinesNotification -> {
            if (magazinesNotification.getResultCode() == Activity.RESULT_OK && log.getState() == Log.Integer.DIRTY )
            {
                Toast.makeText(activity, "New magazines to download! " + magazinesNotification.getMagazines().size(), Toast.LENGTH_LONG ).show();
                startLoader();
            }
        });

        networkReceiver.register(this);
        networkReceiver.refresh();
        refreshList(false);
    }

    @Override
    public void pause() {

        magazineDispatcher.unsubscribe( magazineSubscription );
        magazineNotificationSubject.unsubscribe(notificationSubscription);
        networkReceiver.unregister();
    }

    @Override
    public void refreshList(Boolean aggressive){

        if( aggressive && NetworkUtil.isConnected(activity) )
        {
            getLatestMagazines();
        }
        else
        {
            if ( log.getState() == Log.Integer.CLEAN ) {
                feedListView();
            }
            else {
                startLoader();
            }
        }
    }


    /**
     * v.01 we want to detect and add new magazines to the list
     * before we do the query
     *
     * v.02 we want to detect connection to do v.01 otherwise
     * just do the query and populate any magazine listing left
     */
    private void getLatestMagazines() {

        if(NetworkUtil.isConnected(activity))
        {
            Intent i = new Intent( activity, MagazineListService.class );
            activity.startService(i);
        }
        else
        {
            startLoader();
        }
    }

    private void startLoader(){
        if( activity.getLoaderManager().getLoader(1) != null ){
            activity.getLoaderManager().restartLoader(1, null, this);
        }else{
            activity.getLoaderManager().initLoader(1, null, this);
        }
    }

    @Override
    public void loadMagazine() {
        downloadProxy.startService(this);
    }

    @Override
    public void onNetworkStatus(Boolean connected, String type) {

       view.onNetworkStatus(connected, type);
    }

    @Override
    public void onDownloadResult(int resultCode) {
        adapter.notifyDataSetChanged();
    }


    /**
     * In my humble opinion, I gotta say I feel so glad with MVP for the fact notifications from loaders
     * can be in the presenter rather than the view and ease the code there.
     */
    @Override
    public Loader<ArrayList<Magazine>> onCreateLoader(int id, Bundle args) {
        loader = new MagazineLoader( activity );
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Magazine>> loader, ArrayList<Magazine> data ) {

        magazines.clear();
        magazines.addAll(data);

        feedListView();

        if( log.getState() == Log.Integer.INIT )
        {
            refreshList(true);
        }
        else
        {
            log.setState( Log.Integer.CLEAN );
        }
    }

    private void feedListView(){
        adapter.addAll(magazines);
        adapter.notifyDataSetChanged();
        view.onMagazineList();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Magazine>> loader) {

    }
}