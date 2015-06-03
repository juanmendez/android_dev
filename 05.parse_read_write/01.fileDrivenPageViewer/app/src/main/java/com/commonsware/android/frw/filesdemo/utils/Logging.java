package com.commonsware.android.frw.filesdemo.utils;

import android.util.Log;

/**
 * Created by Juan on 4/18/2015.
 */
public class Logging
{
    public static final String TAG = "filesdemo";

    public static void print(String content)
    {
        Log.i(TAG, content);
    }

    public static void print(int content)
    {
        Log.i(TAG, String.valueOf(content));
    }
}
