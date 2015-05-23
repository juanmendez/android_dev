package com.commonsware.android.frw.filesdemo;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.ThreadEnforcer;

import org.androidannotations.annotations.EBean;

/**
 * Singleton object which handles bus instance and which produces events for listeners
 */
@EBean(scope = EBean.Scope.Singleton)
public class BusHandler {

    private Bus bus;
    private String content;
    private Exception exception;

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

    public void requestContent(String s)
    {
        content = s;
        bus.post(s);
    }

    @Produce
    public String produceContent()
    {
        return content;
    }

    public void requestException(Exception e)
    {
        exception = e;
        bus.post(exception);
    }

    @Produce
    public Exception produceException()
    {
        return exception;
    }
}
