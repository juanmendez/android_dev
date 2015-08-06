package info.juanmendez.android.intentservice.module;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.android.intentservice.ListMagazines;
import info.juanmendez.android.intentservice.MagazineApp;
import info.juanmendez.android.intentservice.MagazineRow;
import info.juanmendez.android.intentservice.MainActivity;
import info.juanmendez.android.intentservice.TestObject;
import info.juanmendez.android.intentservice.helper.DownloadProxy;
import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.service.downloading.MagazineDispatcher;
import info.juanmendez.android.intentservice.service.downloading.DownloadService;

@Module( injects= { MagazineApp.class,
        DownloadService.class,
        MagazineRow.class,
        TestObject.class}, library = true)
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
    Magazine getCurrentMagazine( MagazineDispatcher dispatcher){
        return dispatcher.getMagazine();
    }
}
