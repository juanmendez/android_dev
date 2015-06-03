package com.commonsware.android.frw.filesdemo.model;

import android.view.MenuItem;

/**
 * Created by Juan on 6/1/2015.
 */
public class MenuItemEvent
{
    MenuItem _menuItem;

    public MenuItemEvent( MenuItem menuItem )
    {
        _menuItem = menuItem;
    }

    public MenuItem getMenuItem()
    {
        return _menuItem;
    }

}
