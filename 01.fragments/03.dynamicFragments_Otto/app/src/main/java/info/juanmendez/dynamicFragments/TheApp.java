package info.juanmendez.dynamicFragments;

import android.app.Application;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.ThreadEnforcer;

import info.juanmendez.dynamicFragments.models.ValueChangedEvent;

public class TheApp extends Application
{
    private Bus bus  = new Bus( ThreadEnforcer.MAIN);

    private ValueChangedEvent event;
    public static final String tag = "dynamicFragments";

    public void register( Object o )
    {
        bus.register( o );
    }

    public void unregister( Object o )
    {
        bus.unregister( o );
    }

    public void requestValueChanged(ValueChangedEvent e)
    {
        event = e;
        bus.post( event );
    }

    @Produce
    public ValueChangedEvent produceValueEvent()
    {
        return event;
    }

    public static void consoleLog( String content )
    {
        Log.i(tag, content);
    }
}
