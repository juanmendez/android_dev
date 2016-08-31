package info.juanmendez.realminit.services;

import info.juanmendez.realminit.models.Band;
import info.juanmendez.realminit.models.Song;
import io.realm.Realm;
import rx.Observable;
import rx.subjects.PublishSubject;

public class SongService{

    Realm realm;
    PublishSubject<Song> subject;

    public SongService(Realm _realm) {
        super();
        subject = PublishSubject.create();
        realm = _realm;
    }

    public void add( Song song ){

        /*bandId is required as adding a song from a band
          which was queried from main ui thread won't work
         */

        int bandId = 0;

        if( song.getBand() != null )
            bandId = song.getBand().getId();

        final int finalBandId = bandId;

        realm.executeTransactionAsync(thisRealm->{

            //assign song.id
            Number songId = thisRealm.where(Song.class).max("id");
            if( songId != null ){
                song.setId( songId.intValue() + 1 );
            }else{
                song.setId( 0 );
            }

            //reassign band queried from this thread
            Band band = thisRealm.where(Band.class).equalTo( "id", finalBandId).findFirst();

            if( band != null ){
                song.setBand( band );
            }else{
                song.setBand( null );
            }


            /*you can create an instance of an object first and add it later using realm.copyToRealm()*/
            thisRealm.copyToRealm( song );
        },
        ()-> subject.onNext( song ),
        error-> subject.onError( error ));
    }

    public Observable<Song> getObserver() {
        return subject.asObservable();
    }
}