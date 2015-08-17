package info.juanmendez.android.intentservice.service.network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.helper.NetworkUtil;

/**
 * Created by Juan on 8/17/2015.
 */
public class NetworkReceiver extends BroadcastReceiver
{
    Activity activity;
    NetworkUpdate networkUpdate;

    @Inject
    public NetworkReceiver( Activity updater ){
        super();

        try{
            activity = updater;
            networkUpdate = (NetworkUpdate) updater;
        }catch( ClassCastException e ){

            throw new IllegalStateException( updater.getClass().getSimpleName() +
                    " does not implement contract interface" +
                    getClass().getSimpleName(), e );
        }
    }

    public void register(){
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver( this, filter );
    }

    public void unregister(){
        activity.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        networkUpdate.onNetworkStatus(NetworkUtil.isConnected(context), NetworkUtil.getConnectivityStatusString(context) );
    }
}
