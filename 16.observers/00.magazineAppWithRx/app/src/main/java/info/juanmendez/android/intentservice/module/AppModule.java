package info.juanmendez.android.intentservice.module;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.model.pojo.Log;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.MagazineNotificationSubject;
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
    MagazineDispatcher providesMagazineDispatcher(){
        return new MagazineDispatcher();
    }

    @Provides
    @Singleton
    MagazineNotificationSubject providesMagazineNotificationSubject(){
        return new MagazineNotificationSubject();
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
    NotificationManager providesNotificationManager(){
        return (NotificationManager)_app.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    SqlBrite providesSqlBrite(){
        return SqlBrite.create();
    }
}
