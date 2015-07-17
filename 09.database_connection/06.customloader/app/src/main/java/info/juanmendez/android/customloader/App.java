package info.juanmendez.android.customloader;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Juan on 7/13/2015.
 */
public class App extends Application
{
    private final Bus bus = new Bus(ThreadEnforcer.ANY);

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Bus getBus(){
        return bus;
    }
}
