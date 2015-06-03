package com.commonsware.android.frw.filesdemo.model;

import com.commonsware.android.frw.filesdemo.TheApp;

import dagger.Module;

/**
 * Created by Juan on 5/30/2015.
 */
@Module( injects = {
        TheApp.class
}, library = true )
public class AppModule
{
    private final TheApp app;

    public AppModule( TheApp application ){
        this.app = application;
    }
}
