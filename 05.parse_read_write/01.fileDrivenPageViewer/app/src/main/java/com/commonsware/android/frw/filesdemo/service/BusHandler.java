package com.commonsware.android.frw.filesdemo.service;

import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.model.MenuItemEvent;
import com.commonsware.android.frw.filesdemo.utils.Logging;
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
    private Exception _exception;
    private ActionEvent _event;
    private MenuItemEvent _menuItemEvent;

    BusHandler(){
        bus = new Bus(ThreadEnforcer.MAIN );
    }

    public void register( Object o )
    {
        try{
            bus.register(o);
        }
        catch (Exception e )
        {
            Logging.print( "bus register exception: " + e.getMessage() );
        }
    }

    /**
     * It seems like the pagefragment is discarded, and this can cause
     * an error if just trying to unregister it.
     * @param o
     */
    public void unregister( Object o )
    {
       try{
           bus.unregister(o);
       }
       catch (Exception e )
       {
           Logging.print( "bus unregister exception: " + e.getMessage() );
       }
    }

    public void requestMenuItem( MenuItemEvent menuItemEvent )
    {
        _menuItemEvent = menuItemEvent;
        bus.post(_menuItemEvent);
    }

    @Produce
    public MenuItemEvent produceMenuItem()
    {
        return _menuItemEvent;
    }

    public void requestException(Exception e)
    {
        _exception = e;
        bus.post(_exception);
    }

    @Produce
    public Exception produceException()
    {
        return _exception;
    }


    public void requestEvent(ActionEvent e)
    {
        _event = e;
        bus.post(_event);
    }

    @Produce
    public ActionEvent produceActionEvent()
    {
        return _event;
    }
}
