package info.juanmendez.dynamicfragments.modules;

import com.squareup.otto.ThreadEnforcer;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.dynamicfragments.controllers.TheApplication;
import info.juanmendez.dynamicfragments.models.BusEvent;

/**
 * Created by Juan on 4/18/2015.
 */
@Module(
        injects = { TheApplication.class },
        library = true )
public class AppModule {

    private final TheApplication app;

    public AppModule( TheApplication application ){
        this.app = application;
    }

    @Provides
    ThreadEnforcer threadEnforcerProvider()
    {
        return ThreadEnforcer.MAIN;
    }
}
