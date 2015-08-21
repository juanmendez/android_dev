package info.juanmendez.android.androidconnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.viralpatel.network.NetworkUtil;

/**
 * Created by Juan on 8/10/2015.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();

        debugIntent( intent, "NetworkChangeReceiver");
    }

    /**
     * @see <a href='http://www.grokkingandroid.com/android-getting-notified-of-connectivity-changes'>Getting Notified of Connectivity Changes</a>
     * @param intent
     * @param tag
     */
    private void debugIntent(Intent intent, String tag) {
        Log.v(tag, "action: " + intent.getAction());
        Log.v(tag, "component: " + intent.getComponent());
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key: extras.keySet()) {
                Log.v(tag, "key [" + key + "]: " +
                        extras.get(key));
            }
        }
        else {
            Log.v(tag, "no extras");
        }
    }
}
