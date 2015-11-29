package info.juanmendez.android.intentservice.service.magazine;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import java.util.ArrayList;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.helper.MagazineUtil;
import info.juanmendez.android.intentservice.model.pojo.Log;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.MagazineNotificationSubject;
import info.juanmendez.android.intentservice.model.pojo.MagazinesNotification;
import info.juanmendez.android.intentservice.service.alarm.WakeReceiver;
import info.juanmendez.android.intentservice.ui.ListMagazinesActivity;
import info.juanmendez.android.intentservice.ui.MagazineApp;

/**
 * Created by Juan on 8/2/2015.
 */
public class MagazineListService extends IntentService
{
    @Inject
    Log log;


    @Inject
    MagazineNotificationSubject notificationSubject;

    @Inject
    NotificationManager notificationManager;

    MagazinesNotification magazinesNotification = new MagazinesNotification();


    public MagazineListService() {

        super("magazine_list");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        MagazineApp app = ((MagazineApp) getApplication());
        app.inject(this);

        Uri uri = Uri.parse("content://" + BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider" + "/magazines");
        ContentResolver resolver = app.getContentResolver();
        RetroService service = MagazineService.getService(app.getLocalhost());
        ResultReceiver rec = intent.getParcelableExtra("receiver");
        Boolean isWakeful = intent.getBooleanExtra( "wakeful", false );

        try{
            ArrayList<Magazine> magazines = service.getIssues();
            ArrayList<Magazine> addedMagazines = magazinesNotification.getMagazines();
            addedMagazines.clear();
            Boolean dirty = false;

            //we want to let the application if there has been at least one
            //successful record added. otherwise, we can set the state to clean.
            Uri result;

            for( Magazine m: magazines ){

                result = resolver.insert( uri, MagazineUtil.toContentValues(m));

                if( result != null )
                    addedMagazines.add(m);

                dirty |= (result!=null);

            }

            if( dirty )
                log.setState(Log.Integer.DIRTY);
            else
                log.setState(Log.Integer.CLEAN);

            magazinesNotification.setResultCode(Activity.RESULT_OK);

            if( rec != null )
                rec.send(Activity.RESULT_OK, new Bundle());

        }
        catch( Exception e ){
            e.printStackTrace();

            if( rec != null )
                rec.send( Activity.RESULT_CANCELED, new Bundle());

            magazinesNotification.setResultCode(Activity.RESULT_CANCELED);
        }
        finally{
            if( isWakeful )
            {
                WakeReceiver.completeWakefulIntent(intent);
            }

            //https://github.com/square/otto/issues/38
            //this condition is allow if Log.state = Dirty
            if( log.getState() == Log.Integer.DIRTY )
            {
                if( notificationSubject.hasListener() )
                {
                    notificationSubject.nextOnMain( magazinesNotification );
                }
                else
                {
                    notifyMagazines();
                }

            }
        }
    }

    private  void notifyMagazines(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle( "there are new magazines to download" )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("# " + magazinesNotification.getMagazines().size() )
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(this, ListMagazinesActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
