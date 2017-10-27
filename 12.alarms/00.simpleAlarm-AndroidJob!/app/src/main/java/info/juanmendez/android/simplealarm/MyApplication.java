package info.juanmendez.android.simplealarm;

import android.app.Application;

import com.evernote.android.job.JobManager;

import info.juanmendez.android.simplealarm.androidjob.DemoJobCreator;
import timber.log.Timber;


/**
 * Created by Juan Mendez on 10/20/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant( new Timber.DebugTree());
        JobManager.create(this).addJobCreator( new DemoJobCreator() );
    }
}
