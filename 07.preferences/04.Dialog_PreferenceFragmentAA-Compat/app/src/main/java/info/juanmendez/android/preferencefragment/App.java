package info.juanmendez.android.preferencefragment;

import android.app.Application;

import timber.log.Timber;


/**
 * Created by Juan Mendez on 9/16/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.Tree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
                return;
            }
        });
    }
}
