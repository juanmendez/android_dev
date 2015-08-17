package info.juanmendez.android.simplealarm;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Juan on 8/15/2015.
 */
public class WakeupService extends Service{

    private Boolean running = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        running = true;
        run(intent);

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        running = false;
        super.onDestroy();
    }

    private void run(final Intent intent) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep( 90000 );

                    if( running )
                    {
                        WakeupService.this.notify("ring, ring, ring");
                        WakeReceiver.completeWakefulIntent(intent);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    private void notify( final String messageName) {

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
                .setContentTitle(messageName + " " + strings[strings.length - 1])
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(name)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
