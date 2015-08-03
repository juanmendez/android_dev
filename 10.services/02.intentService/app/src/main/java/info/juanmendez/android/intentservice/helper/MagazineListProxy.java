package info.juanmendez.android.intentservice.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.service.downloading.DownloadService;
import info.juanmendez.android.intentservice.service.versioning.MagazineListService;

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

    public interface UiCallBack {
        public void onReceiveResult( int resultCode );
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        callback.onReceiveResult( resultCode );
    }
}