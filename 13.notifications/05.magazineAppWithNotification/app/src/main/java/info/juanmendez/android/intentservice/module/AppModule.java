package info.juanmendez.android.intentservice.module;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.model.pojo.Log;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.NotificationListener;
import info.juanmendez.android.intentservice.model.pojo.Page;
import info.juanmendez.android.intentservice.service.download.DownloadService;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;
import info.juanmendez.android.intentservice.service.magazine.MagazineListService;
import info.juanmendez.android.intentservice.ui.MagazineApp;
import info.juanmendez.android.intentservice.ui.MagazineRow;

@Module( injects= { MagazineApp.class,
        DownloadService.class,
        MagazineListService.class,
        MagazineRow.class}, library = true)
public class AppModule {

    Application _app;

    public AppModule( Application app ){
        _app = app;
    }

    @Provides
    @Singleton
    Bus getBus()
    {
        return new Bus(ThreadEnforcer.ANY);
    }

    @Provides
    @Singleton
    MagazineDispatcher getDispatcher(Bus bus){
        return new MagazineDispatcher(bus);
    }

    @Provides
    @Singleton
    ArrayList<Page> providesPages(){
        return new ArrayList<Page>();
    }

    @Provides
    @Singleton
    ArrayList<Magazine> provideMagazines(){
        return new ArrayList<Magazine>();
    }

    @Provides
    @Singleton
    Log providesLog(){
       return new Log();
    }

    @Provides
    @Singleton
    NotificationListener providesNotificationListener(){
        return new NotificationListener();
    }

    @Provides
    NotificationManager providesNotificationManager(){
        return (NotificationManager)_app.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
