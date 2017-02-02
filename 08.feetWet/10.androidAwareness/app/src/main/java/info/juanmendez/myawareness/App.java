package info.juanmendez.myawareness;

import android.app.Application;

import info.juanmendez.myawareness.service.AppComponent;


/**
 * Created by Juan Mendez on 1/26/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate(){
        super.onCreate();
    }
}
