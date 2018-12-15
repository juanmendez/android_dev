package info.juanmendez.realminit;

import android.app.Application;

import dagger.ObjectGraph;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by musta on 8/20/2016.
 */
public class RealmApplication extends Application {

    private static ObjectGraph applicationGraph;

    @Override
    public void onCreate(){
        super.onCreate();

        RealmApplication.applicationGraph = ObjectGraph.create( new RootModule(this));

        /**
         * lets set the default Realm (database)
         * here, so it's done for us just once
         */
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("info.juanmendez.realm.band_artist")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration( realmConfiguration );
    }

    public static void inject( Object object ){
        applicationGraph.inject( object );
    }

}
