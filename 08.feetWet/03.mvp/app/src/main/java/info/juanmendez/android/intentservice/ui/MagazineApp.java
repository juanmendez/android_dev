package info.juanmendez.android.intentservice.ui;

import android.app.Application;

import dagger.ObjectGraph;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.module.AppModule;

/**
 * Created by Juan on 8/1/2015.
 */
public class MagazineApp extends Application
{
    private ObjectGraph graph;

    @Override
    public void onCreate()
    {
        super.onCreate();

        graph = ObjectGraph.create( new AppModule(this));
        inject(this);
    }

    public void inject( Object object )
    {
        graph.inject(object);
    }

    public ObjectGraph getGraph() {
        return graph;
    }

    public String getLocalhost()
    {
        return getResources().getString( R.string.localhost );
    }
}
