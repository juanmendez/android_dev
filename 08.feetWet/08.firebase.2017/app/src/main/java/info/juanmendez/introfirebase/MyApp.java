package info.juanmendez.introfirebase;

import android.app.Application;

import org.androidannotations.annotations.EApplication;

import timber.log.Timber;

/**
 * Created by juan on 12/8/17.
 */

@EApplication
public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant( new Timber.DebugTree());
    }
}
