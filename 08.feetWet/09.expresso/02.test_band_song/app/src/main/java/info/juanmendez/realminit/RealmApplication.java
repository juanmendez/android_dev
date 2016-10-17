package info.juanmendez.realminit;

import android.app.Application;
import dagger.ObjectGraph;

/**
 * Created by musta on 8/20/2016.
 */
public class RealmApplication extends Application {

    private static ObjectGraph applicationGraph;

    @Override
    public void onCreate(){
        super.onCreate();

        RealmApplication.applicationGraph = ObjectGraph.create( new RootModule(this));
    }

    public static void inject( Object object ){
        applicationGraph.inject( object );
    }

}
