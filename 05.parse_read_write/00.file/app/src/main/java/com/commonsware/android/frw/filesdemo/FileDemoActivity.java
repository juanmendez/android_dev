package com.commonsware.android.frw.filesdemo;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.main)
public class FileDemoActivity extends ActionBarActivity {

    @AfterViews
    void afterViews()
    {
        if (BuildConfig.DEBUG
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            StrictMode.setThreadPolicy(buildPolicy());
        }
    }

    private StrictMode.ThreadPolicy buildPolicy()
    {
        return (new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog()
                .build());
    }
}
