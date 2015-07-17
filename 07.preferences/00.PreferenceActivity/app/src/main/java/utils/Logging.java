package utils;

import android.util.Log;

/**
 * Created by Juan on 4/18/2015.
 */
public class Logging
{
    public static final String TAG = "00.PreferenceActivity";

    public static void print(String content)
    {
        Log.i(TAG, content);
    }

    public static void print(int content)
    {
        Log.i(TAG, String.valueOf(content));
    }
}
