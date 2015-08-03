package info.juanmendez.android.intentservice.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.service.downloading.DownloadService;
import info.juanmendez.android.intentservice.service.provider.SQLMagazine;

/**
 * The intent to invoke service wraps an instance of this class. That same service is able to get the
 * object from the intent received, and is able to call send method which is handled by onReceiveResult.
 *
 * Having a reference of our callback, we can call a specific method.
 */
//TODO maybe stay away from Dagger here.
public class DownloadProxy extends ResultReceiver
{
    private UiCallback callback;

    public DownloadProxy(){
        super( new Handler());
    }

    public void startService( Activity activity, UiCallback  callback){

        this.callback = callback;

        Intent i = new Intent( activity, DownloadService.class );
        i.putExtra("receiver", this);
        i.putExtra( "zipUrl", (BuildConfig.DEBUG ? "http://192.168.45.1" : "http://ketchup") + "/development/android/magazine/mag_0.1/www.zip" );
        i.putExtra("version", 0.1f);
        activity.startService(i);
    }

    public interface UiCallback {
        public void onReceiveResult( int resultCode, Magazine magazine );
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        super.onReceiveResult(resultCode, resultData);

        Magazine magazine = null;

        if( resultCode == Activity.RESULT_OK )
        {
            magazine = MagazineParser.fromBundle( resultData );
        }

        callback.onReceiveResult( resultCode, magazine );
    }
}