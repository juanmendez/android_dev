package info.juanmendez.android.intentservice.service.network;

/**
 * Created by Juan on 8/17/2015.
 */
public interface NetworkUpdate {

    void onNetworkStatus( Boolean connected, String type );
}
