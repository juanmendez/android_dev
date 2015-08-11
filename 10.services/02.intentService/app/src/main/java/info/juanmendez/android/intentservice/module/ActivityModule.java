package info.juanmendez.android.intentservice.module;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.ListMagazines;
import info.juanmendez.android.intentservice.MagazineActivity;
import info.juanmendez.android.intentservice.helper.DownloadProxy;
import info.juanmendez.android.intentservice.model.WebViewAdapter;
import info.juanmendez.android.intentservice.service.provider.MagazineLoader;

/**
 * Created by Juan on 7/29/2015.
 */
@Module(
injects = {
        ListMagazines.class,
        MagazineActivity.class,
        WebViewAdapter.class
},
        addsTo = AppModule.class,
        library = true
)
public class ActivityModule
{
    private final AppCompatActivity activity;

    public ActivityModule( AppCompatActivity activity ){
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
