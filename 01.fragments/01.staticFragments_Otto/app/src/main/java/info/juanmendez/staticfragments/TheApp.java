package info.juanmendez.staticfragments;

import android.app.Application;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import info.juanmendez.staticfragments.controllers.MainActivity;

/**
 * Created by Juan on 3/31/2015.
 */
public class TheApp extends Application
{
    private static Bus BUS;

    public static Bus getOtto() {

        if( BUS == null )
        {
            Log.i(MainActivity.tag, "bus singleton created");
            BUS  = new Bus( ThreadEnforcer.MAIN);
        }

        return BUS;
    }

}
