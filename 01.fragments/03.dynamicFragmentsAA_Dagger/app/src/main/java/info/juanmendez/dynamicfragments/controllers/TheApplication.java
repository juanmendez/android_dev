package info.juanmendez.dynamicfragments.controllers;

import android.app.Application;

import javax.inject.Inject;

import dagger.ObjectGraph;
import info.juanmendez.dynamicfragments.models.BusEvent;
import info.juanmendez.dynamicfragments.models.LoggingManager;
import info.juanmendez.dynamicfragments.modules.AppModule;

/**
 * Created by Juan on 4/18/2015.
 */
public class TheApplication extends Application
{
    private ObjectGraph graph;

    @Inject
    BusEvent busEvent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        graph = ObjectGraph.create( new AppModule(this) );
        graph.inject( this );

        if( busEvent != null )
        {

        }
    }

    ObjectGraph getApplicationGraph(){
        return graph;
    }
}
