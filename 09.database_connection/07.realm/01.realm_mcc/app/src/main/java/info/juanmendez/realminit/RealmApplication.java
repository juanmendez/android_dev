package info.juanmendez.realminit;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by musta on 8/20/2016.
 */
public class RealmApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        /**
         * lets set the default Realm (database)
         * here, so it's done for us just once
         */
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("info.juanmendez.realm")
                .build();
        Realm.setDefaultConfiguration( realmConfiguration );
    }
}
