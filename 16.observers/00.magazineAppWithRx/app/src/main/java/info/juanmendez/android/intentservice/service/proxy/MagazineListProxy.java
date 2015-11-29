package info.juanmendez.android.intentservice.service.proxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import info.juanmendez.android.intentservice.service.magazine.MagazineListService;

/**
 * This Activity/Service plugin has been deprecated in favor of MagNotifications
 */
@Deprecated
public class MagazineListProxy extends ResultReceiver
{
    protected UiCallBack callback;

    public MagazineListProxy(){
        super( new Handler());
    }

    public void startService( Activity activity, UiCallBack callback){

        this.callback = callback;

        Intent i = new Intent( activity, MagazineListService.class );
        i.putExtra("receiver", this);
        activity.startService(i);
    }

    @Deprecated
    public interface UiCallBack {
        public void onMagazineListResult( int resultCode );
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        callback.onMagazineListResult( resultCode );
    }
}