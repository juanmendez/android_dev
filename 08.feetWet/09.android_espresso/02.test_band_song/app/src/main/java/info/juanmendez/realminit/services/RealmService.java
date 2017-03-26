package info.juanmendez.realminit.services;

import android.app.Application;

import java.io.FileNotFoundException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import android.content.Context;

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

        //Realm.deleteRealm(realmConfiguration);

        try {
            Realm.migrateRealm( realmConfiguration, new MyMigration() );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Realm.setDefaultConfiguration( realmConfiguration );

        return Realm.getDefaultInstance();
    }
}
