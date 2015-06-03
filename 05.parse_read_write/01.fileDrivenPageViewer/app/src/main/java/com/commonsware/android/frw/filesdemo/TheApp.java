package com.commonsware.android.frw.filesdemo;

import android.app.Application;
import com.commonsware.android.frw.filesdemo.service.AppModule;

import dagger.ObjectGraph;

public class TheApp extends Application
{
    private ObjectGraph graph;

    @Override
    public void onCreate()
    {
        super.onCreate();
        graph = ObjectGraph.create( new AppModule(this) );
        graph.inject( this );
    }

    ObjectGraph getApplicationGraph(){
        return graph;
    }
}