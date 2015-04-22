package info.juanmendez.dynamicfragments.models;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Juan on 4/18/2015.
 */
@Singleton
public class BusEvent
{
    private final Bus bus;
    private ValueChangedEvent event;

    @Inject
    public BusEvent(ThreadEnforcer threadEnforcer)
    {
        bus = new Bus( threadEnforcer );
    }

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
}
