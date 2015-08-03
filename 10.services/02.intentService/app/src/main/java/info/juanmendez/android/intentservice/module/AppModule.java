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
import info.juanmendez.android.intentservice.helper.DownloadProxy;
import info.juanmendez.android.intentservice.service.downloading.DownloadDispatcher;
import info.juanmendez.android.intentservice.service.downloading.DownloadService;

@Module( injects= { MagazineApp.class, MainActivity.class, DownloadService.class, ListMagazines.class, MagazineRow.class}, complete = false, library = true)
public class AppModule {

    Application _app;

    public AppModule( Application app ){
        _app = app;
    }

    @Provides
    DownloadProxy receiver(){
        return new DownloadProxy();
    }


    @Provides
    @Singleton
    Bus getBus()
    {
        return new Bus(ThreadEnforcer.ANY);
    }

    @Provides
    @Singleton
    DownloadDispatcher getDispatcher(Bus bus){
        return new DownloadDispatcher(bus);
    }

}
