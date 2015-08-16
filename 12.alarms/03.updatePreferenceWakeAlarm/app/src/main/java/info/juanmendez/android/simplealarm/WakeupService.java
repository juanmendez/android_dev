package info.juanmendez.android.simplealarm;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
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
        WakeReceiver.completeWakefulIntent(intent);
    }

    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    private void notify( final String methodName) {

        /**
         * From the back of my head, I remember this features in another tutorial
         * http://www.vogella.com/tutorials/AndroidLifeCycle/article.html
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ){

            String name = this.getClass().getName();
            String[] strings = name.split("\\.");

            Notification noti;

            Notification.Builder builder = new Notification.Builder(this)
                    .setContentTitle(methodName + " " + strings[strings.length - 1]).setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher )
                    .setContentText(name);

            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
                noti = builder.build();
            else
                noti = builder.getNotification();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify((int) System.currentTimeMillis(), noti);
        }else{
            Handler handler = new Handler(Looper.getMainLooper() );

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), methodName, Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
