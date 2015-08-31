package info.juanmendez.android.intentservice.service.magazine;

import android.app.Activity;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.model.pojo.Log;
import info.juanmendez.android.intentservice.service.alarm.WakeReceiver;
import info.juanmendez.android.intentservice.ui.MagazineApp;
import info.juanmendez.android.intentservice.helper.MagazineUtil;
import info.juanmendez.android.intentservice.model.pojo.Magazine;

/**
 * Created by Juan on 8/2/2015.
 */
public class MagazineListService extends IntentService
{
    @Inject
    Log log;

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
            Boolean dirty = false;

            //we want to let the application if there has been at least one
            //successful record added. otherwise, we can set the state to clean.
            Uri result;

            for( Magazine m: magazines ){

                result = resolver.insert( uri, MagazineUtil.toContentValues(m));
                dirty |= (result!=null);
            }

            if( dirty )
                log.setState(Log.Integer.DIRTY);
            else
                log.setState(Log.Integer.CLEAN);

            if( rec != null )
                rec.send(Activity.RESULT_OK, new Bundle());

        }
        catch( Exception e ){
            e.printStackTrace();

            if( rec != null )
                rec.send( Activity.RESULT_CANCELED, new Bundle());
        }
        finally{
            if( isWakeful )
            {
                WakeReceiver.completeWakefulIntent(intent);
            }
        }
    }
}
