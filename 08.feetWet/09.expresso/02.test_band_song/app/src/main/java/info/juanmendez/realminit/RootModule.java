package info.juanmendez.realminit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.realminit.models.BandCom;
import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.models.SongCom;
import info.juanmendez.realminit.models.SongStatus;
import info.juanmendez.realminit.services.BandService;
import info.juanmendez.realminit.services.RealmService;
import info.juanmendez.realminit.services.SongService;
import info.juanmendez.realminit.views.BandFormDialog;
import info.juanmendez.realminit.views.BandsFragment_;
import info.juanmendez.realminit.views.MainActivity_;
import info.juanmendez.realminit.views.SongFormDialog_;
import info.juanmendez.realminit.views.SongsFragment_;
import io.realm.Realm;
import rx.subjects.PublishSubject;

/**
 * Created by musta on 8/25/2016.
 */
@Module(
        injects = {MainActivity_.class,
                  SongsFragment_.class,
                  SongFormDialog_.class,
                   BandsFragment_.class,
                   BandFormDialog.class },
        library = true)
public class RootModule {

    private final RealmApplication application;

    public RootModule(RealmApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Realm provideRealmDefaultInstance(){
        return RealmService.createRealm( application );
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

    @Provides
    @Singleton
    SongStatus provideSongStatus(){
        return new SongStatus();
    }

    @Provides
    @Singleton
    PublishSubject<SongCom> songSubject(){
        return PublishSubject.create();
    }

    @Provides
    @Singleton
    PublishSubject<BandCom> bandSubject(){
        return PublishSubject.create();
    }
}
