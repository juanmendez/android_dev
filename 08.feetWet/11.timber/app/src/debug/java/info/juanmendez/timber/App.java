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

        //make timber work on debug version
        Timber.plant(new Timber.DebugTree(){
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + " " + element.getClassName() + ": " + element.getLineNumber();
            }
        });
    }
}
