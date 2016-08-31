package info.juanmendez.realminit.services;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class MyMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        //we have added Song.band to version 1.1
        if( oldVersion == 0 ){
            schema.get( "Song" ).addRealmObjectField("band", schema.get("Band") );
        }
    }
}
