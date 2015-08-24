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
    public NetworkReceiver( Activity activity ){
        super();
        this.activity = activity;
    }

    public void register( NetworkUpdate updater){
        networkUpdate =  updater;
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(this, filter);
    }

    public void unregister(){
        networkUpdate = null;
        activity.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if( networkUpdate != null ){
            networkUpdate.onNetworkStatus(NetworkUtil.isConnected(context), NetworkUtil.getConnectivityStatusString(context) );
        }
    }
}
