package info.juanmendez.dynamicfragments.models;

import android.util.Log;

import javax.inject.Singleton;

/**
 * Created by Juan on 4/18/2015.
 */
public class LoggingManager
{
    public static final String TAG = "dagger.juanmendez.info";

    public static void consoleLog( String content )
    {
        Log.i(TAG, content);
    }
}
