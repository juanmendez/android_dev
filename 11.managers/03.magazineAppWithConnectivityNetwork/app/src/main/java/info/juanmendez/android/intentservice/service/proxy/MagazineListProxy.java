package info.juanmendez.android.intentservice.service.proxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import info.juanmendez.android.intentservice.helper.NetworkUtil;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;

public class MagazineListProxy extends ResultReceiver
{
    protected UiCallBack callback;

    public MagazineListProxy(){
        super( new Handler());
    }

    public void startService( Activity activity, UiCallBack callback){

        this.callback = callback;

        if(NetworkUtil.isConnected(activity))
        {
            Intent i = new Intent( activity, MagazineListService.class );
            i.putExtra("receiver", this);
            activity.startService(i);
        }else{
            callback.onMagazineListResult(Activity.RESULT_CANCELED);
        }
    }

    public interface UiCallBack {
        public void onMagazineListResult( int resultCode );
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        callback.onMagazineListResult( resultCode );
    }
}