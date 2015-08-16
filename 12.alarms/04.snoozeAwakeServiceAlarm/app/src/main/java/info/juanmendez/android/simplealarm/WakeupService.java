package info.juanmendez.android.simplealarm;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Juan on 8/15/2015.
 */
public class WakeupService extends IntentService{
    public WakeupService(){
        super( "wakeupService" );
    }

    @Override
    protected void onHandleIntent( Intent intent ){

        notify("ring, ring, ring");
    }

    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    private void notify( final String methodName) {

        /**
         * From the back of my head, I remember this features in another tutorial
         * http://www.vogella.com/tutorials/AndroidLifeCycle/article.html
         */
        String name = this.getClass().getName();
        String[] strings = name.split("\\.");

        //http://android-er.blogspot.com/2013/03/create-pendingintent-for-notification.html
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(methodName + " " + strings[strings.length - 1])
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(name)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0 ));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
