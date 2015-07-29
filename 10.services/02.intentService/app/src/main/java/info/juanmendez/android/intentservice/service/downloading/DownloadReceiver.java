package info.juanmendez.android.intentservice.service.downloading;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Juan on 7/24/2015.
 *
 * The intent to invoke service wraps an instance of this class. That same service is able to get the
 * object from the intent received, and is able to call send method which is handled by onReceiveResult.
 *
 * Having a reference of our callback, we can call a specific method.
 */
public class DownloadReceiver extends ResultReceiver
{
    private Callback callback;

    public DownloadReceiver(Handler handler) {
        super(handler);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        public void onReceiveResult( int resultCode, Bundle resultData );
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if( callback == null )
        {
            super.onReceiveResult(resultCode, resultData);
        }
        else{
            callback.onReceiveResult( resultCode, resultData );
        }
    }
}
