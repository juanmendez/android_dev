package info.juanmendez.myawareness.service;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Juan Mendez on 1/27/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule( Activity activity ){
        this.activity = activity;
    }

    @Provides
    Activity activity() {
        return activity;
    }
}
