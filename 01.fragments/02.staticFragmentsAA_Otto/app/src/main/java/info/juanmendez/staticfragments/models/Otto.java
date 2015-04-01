package info.juanmendez.staticfragments.models;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.ThreadEnforcer;

import org.androidannotations.annotations.EBean;

@EBean( scope= EBean.Scope.Singleton)
public class Otto
{
    private Bus bus;
    private ValueChangedEvent event;

    Otto()
    {
        bus  = new Bus( ThreadEnforcer.MAIN);
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
