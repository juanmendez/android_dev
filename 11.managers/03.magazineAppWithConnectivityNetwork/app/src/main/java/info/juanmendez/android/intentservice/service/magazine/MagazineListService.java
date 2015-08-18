package info.juanmendez.android.intentservice.service.magazine;

import android.app.Activity;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.ui.MagazineApp;
import info.juanmendez.android.intentservice.helper.MagazineUtil;
import info.juanmendez.android.intentservice.model.pojo.Magazine;

/**
 * Created by Juan on 8/2/2015.
 */
public class MagazineListService extends IntentService
{
    public MagazineListService() {

        super("magazine_list");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        MagazineApp app = ((MagazineApp) getApplication());

        Uri uri = Uri.parse("content://" + BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider" + "/magazines");
        ContentResolver resolver = getContentResolver();
        RetroService service = MagazineService.getService( app.getLocalhost() );
        ResultReceiver rec = intent.getParcelableExtra("receiver");

        try{
            ArrayList<Magazine> magazines = service.getIssues();

            for( Magazine m: magazines ){

                resolver.insert( uri, MagazineUtil.toContentValues(m));
            }

            rec.send(Activity.RESULT_OK, new Bundle());

        }
        catch( Exception e ){
            e.printStackTrace();
            rec.send( Activity.RESULT_CANCELED, new Bundle());
        }
    }
}
