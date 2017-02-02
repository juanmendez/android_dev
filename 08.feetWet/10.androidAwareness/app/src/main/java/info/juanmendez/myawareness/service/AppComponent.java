package info.juanmendez.myawareness.service;

import javax.inject.Singleton;

import dagger.Component;
import info.juanmendez.myawareness.App;

/**
 * Created by Juan Mendez on 1/27/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

@Singleton
@Component(modules= {AppModule.class} )
public interface AppComponent {
    //tell where to inject the module
    void inject( App application );
}
