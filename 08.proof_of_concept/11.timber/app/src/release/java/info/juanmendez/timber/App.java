package info.juanmendez.timber;

import android.app.Application;

import timber.log.Timber;


/**
 * Created by Juan Mendez on 2/20/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //make timber work on release version
        Timber.plant(new ReleaseTree());
    }
}
