package info.juanmendez.myawareness.service;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.myawareness.App;

/**
 * Created by Juan Mendez on 1/26/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

@Module
public class AppModule {

    private final App app;

    public AppModule( App app ){
        this.app = app;
    }

    @Provides
    @Singleton
    public App application(){
        return app;
    }

}
