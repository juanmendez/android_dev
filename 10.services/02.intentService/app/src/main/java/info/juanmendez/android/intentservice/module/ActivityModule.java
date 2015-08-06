package info.juanmendez.android.intentservice.module;

import android.app.Activity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.ListMagazines;
import info.juanmendez.android.intentservice.MainActivity;
import info.juanmendez.android.intentservice.helper.DownloadProxy;

/**
 * Created by Juan on 7/29/2015.
 */
@Module(
injects = {
        ListMagazines.class,
        MainActivity.class
},
        addsTo = AppModule.class,
        library = true
)
public class ActivityModule
{
    private final Activity activity;

    public ActivityModule( Activity activity ){
        this.activity = activity;
    }

    @Provides
    @Singleton
    public Activity activityProvider()
    {
        return this.activity;
    }

    @Provides
    public DownloadProxy providesProxy(Activity activity )
    {
        return new DownloadProxy(activity);
    }
}
