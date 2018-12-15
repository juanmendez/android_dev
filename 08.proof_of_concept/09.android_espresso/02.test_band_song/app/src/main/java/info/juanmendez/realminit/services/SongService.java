package info.juanmendez.realminit.services;

import android.util.Log;

import info.juanmendez.realminit.models.Band;
import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.models.SongCom;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.subjects.PublishSubject;

public class SongService{

    Realm realm;
    PublishSubject<SongCom> subject;

    public SongService(Realm _realm) {
        super();
        subject = PublishSubject.create();
        realm = _realm;
    }

    /**
     * if song id is -1, then we assign an incremented id, otherwise we keep original.
     * @param song
     */
    public void addOrUpdate( Song song ){

        /*bandId is required as adding a song from a band
          which was queried from main ui thread won't work
         */

        final int finalBandId = song.getBand()!=null?song.getBand().getId():-1;

        realm.executeTransactionAsync(thisRealm->{

            //assign song.id
            if( song.getId() < 0 ){
                Number songId = thisRealm.where(Song.class).max("id");
                if( songId != null ){
                    song.setId( songId.intValue() + 1 );
                }else{
                    song.setId( 0 );
                }
            }

            //reassign band queried from this thread
            if( finalBandId >= 0 ){
                Band band = thisRealm.where(Band.class).equalTo( "id", finalBandId).findFirst();
                song.setBand( band );
            }else{
                song.setBand( null );
            }

            /*you can create an instance of an object first and add it later using realm.copyToRealm()*/
            thisRealm.copyToRealmOrUpdate( song );
        },
        ()->{
            SongCom songCom = new SongCom();
            songCom.setStatus( SongCom.ADD );
            songCom.setSong( song);
            subject.onNext( songCom );
        },
        error-> subject.onError( error ));
    }

    public void deleteSong( Song song ){

        if( song!= null ){
            final int songId = song.getId();

            realm.executeTransactionAsync(thisRealm -> {
                RealmResults<Song> songResults = thisRealm.where( Song.class ).equalTo( "id", songId ).findAll();
                songResults.deleteFirstFromRealm();
            }, () -> {

                SongCom songCom = new SongCom();
                songCom.setStatus( SongCom.DELETE );
                songCom.setSong( song);
                songCom.setConfirm( SongCom.COMPLETED );
                subject.onNext( songCom );

            },
            error-> subject.onError( error ));


        }
    }

    public Song getSong( int songId ){

        Song song = realm.where( Song.class ).equalTo("id", songId ).findFirst();
        return song;
    }

    public Observable<SongCom> getObserver() {
        return subject.asObservable();
    }
}