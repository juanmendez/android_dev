package info.juanmendez.android.simplealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Juan on 8/15/2015.
 */
public class WakeReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if( intent.getAction() == null )
        {
           startWakefulService(context, new Intent(context, WakeupService.class));
        }
        else
        {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            Boolean bootPref = settings.getBoolean(MainActivity.BOOTPREF, true);

            if( bootPref )
            {
                startAlarm(context);
            }
        }
    }

    static void startAlarm(Context context)
    {
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent( context, WakeReceiver.class );
        PendingIntent pi = PendingIntent.getBroadcast( context, 0, i , 0 );

        mgr.setRepeating( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5000, 120000, pi );
    }

    static void cancelAlarm(Context context)
    {
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent( context, WakeReceiver.class );
        PendingIntent pi = PendingIntent.getBroadcast( context, 0, i , 0 );
        mgr.cancel( pi );
    }
}