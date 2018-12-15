package info.juanmendez.realminit;

import java.io.FileNotFoundException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.realminit.services.BandService;
import info.juanmendez.realminit.services.MyMigration;
import info.juanmendez.realminit.services.SongService;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by musta on 8/25/2016.
 */
@Module(
        injects = {MainActivity_.class, SongFormDialog_.class, BandFormDialog.class },
        library = true)
public class RootModule {

    private final RealmApplication application;

    public RootModule(RealmApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Realm provideRealmDefaultInstance(){

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(application)
                .name("info.juanmendez.realm.onetomany")
                .schemaVersion( 1 )
                .build();

        try {
            Realm.migrateRealm( realmConfiguration, new MyMigration() );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Realm.setDefaultConfiguration( realmConfiguration );

        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    BandService provideBandService( Realm realm){
        return new BandService(realm);
    }

    @Provides
    @Singleton
    SongService provideSongService( Realm realm){
        return new SongService(realm);
    }
}
