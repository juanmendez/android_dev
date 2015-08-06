package info.juanmendez.android.intentservice.service.versioning;

import android.app.Activity;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.MagazineApp;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.helper.Logging;
import info.juanmendez.android.intentservice.helper.MagazineParser;
import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.model.MagazineService;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;

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
        Uri result;
        ContentResolver resolver = getContentResolver();
        RetroService service = MagazineService.getService( app.getLocalhost() );
        ResultReceiver rec = intent.getParcelableExtra("receiver");

        try{
            ArrayList<Magazine> magazines = service.getIssues();

            for( Magazine m: magazines ){

                result = resolver.insert( uri, MagazineParser.toContentValues(m));
            }

            rec.send(Activity.RESULT_OK, new Bundle());

        }
        catch( Exception e ){
            e.printStackTrace();
            rec.send( Activity.RESULT_CANCELED, new Bundle());
        }
    }
}
