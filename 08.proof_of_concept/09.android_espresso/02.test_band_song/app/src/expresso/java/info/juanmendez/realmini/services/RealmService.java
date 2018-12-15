package info.juanmendez.realmini.services;

import android.content.Context;

import java.io.FileNotFoundException;

import info.juanmendez.realminit.services.MyMigration;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Juan Mendez on 9/11/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
public class RealmService {

    public static Realm createRealm(Context context){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
                .name("info.juanmendez.realm.onetomany")
                .schemaVersion( 1 )
                .build();

        Realm.deleteRealm(realmConfiguration);

        try {
            Realm.migrateRealm( realmConfiguration, new MyMigration() );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Realm.setDefaultConfiguration( realmConfiguration );

        return Realm.getDefaultInstance();
    }
}
