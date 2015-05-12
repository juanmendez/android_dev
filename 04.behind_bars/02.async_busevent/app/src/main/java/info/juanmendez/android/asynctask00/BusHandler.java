package info.juanmendez.android.asynctask00;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.ThreadEnforcer;

import org.androidannotations.annotations.EBean;

/**
 * Created by Juan on 5/11/2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class BusHandler {

    private Bus bus;
    private StringEvent e;

    BusHandler(){
        bus = new Bus(ThreadEnforcer.MAIN );
    }

    public void register( Object o )
    {
        bus.register( o );
    }

    public void unregister( Object o )
    {
        bus.unregister( o );
    }

    public void requestValueChanged(StringEvent event)
    {
        e = event;
        bus.post( e );
    }

    @Produce
    public StringEvent produceValueEvent()
    {
        return e;
    }

}
