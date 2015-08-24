package info.juanmendez.android.intentservice.module;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.ui.ListMagazinesActivity;
import info.juanmendez.android.intentservice.ui.MagazineActivity;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.model.adapter.WebViewAdapter;
import info.juanmendez.android.intentservice.ui.listmagazine.IListMagazinesPresenter;
import info.juanmendez.android.intentservice.ui.listmagazine.ListMagazinesPresenter;
import info.juanmendez.android.intentservice.ui.magazine.MagazinePresenter;

/**
 * Created by Juan on 7/29/2015.
 */
@Module(
injects = {
        ListMagazinesActivity.class,
        ListMagazinesPresenter.class,
        MagazineActivity.class,
        WebViewAdapter.class,
        MagazinePresenter.class
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

    @Provides
    public ConnectivityManager providesConnectivityManager(){
        return (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
