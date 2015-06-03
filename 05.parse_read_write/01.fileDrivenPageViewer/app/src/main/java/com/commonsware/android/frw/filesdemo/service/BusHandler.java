package com.commonsware.android.frw.filesdemo.service;

import android.view.MenuItem;

import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.model.MenuItemEvent;
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
        bus.register( o );
    }

    public void unregister( Object o )
    {
        bus.unregister(o);
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
