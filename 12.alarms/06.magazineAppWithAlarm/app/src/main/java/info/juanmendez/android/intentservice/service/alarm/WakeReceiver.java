package info.juanmendez.android.intentservice.service.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import info.juanmendez.android.intentservice.model.MagazinesPref;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;

/**
 * Created by Juan on 8/28/2015.
 */
public class WakeReceiver extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {

        if( intent.getAction() == null ){

            Intent i = new Intent(context, MagazineListService.class);
            i.putExtra( "wakeful", true );
            startWakefulService(context, i);
        }else{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context );
            Boolean updates = preferences.getBoolean(MagazinesPref.MAGAZINE_UPDATES, false );

            if( updates ){
                startAlarm(context);
            }
        }
    }

    public static void startAlarm( Context context ){
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent( context, WakeReceiver.class );
        PendingIntent pi = PendingIntent.getBroadcast( context, 0, i , 0 );

        mgr.setRepeating( AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 5000,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi );

    }

    public static void cancelAlarm( Context context ){
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent( context, WakeReceiver.class );
        PendingIntent pi = PendingIntent.getBroadcast( context, 0, i , 0 );
        mgr.cancel( pi );
    }
}
