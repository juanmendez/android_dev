package info.juanmendez.android.intentservice.helper;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by Juan on 9/6/2015.
 */
public class BusUtil
{
    private static final Handler mainThread = new Handler(Looper.getMainLooper());

    public static void postOnMain( final Bus bus, final Object event) {

        mainThread.post(() -> bus.post(event));
    }
}
