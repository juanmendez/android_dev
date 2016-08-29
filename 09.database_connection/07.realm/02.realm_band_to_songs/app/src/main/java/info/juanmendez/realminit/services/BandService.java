package info.juanmendez.realminit.services;

import info.juanmendez.realminit.models.Band;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by musta on 8/27/2016.
 */
public class BandService{

    Realm realm;
    PublishSubject<Band> subject;

    public BandService(Realm _realm) {
        super();
        subject = PublishSubject.create();
        realm = _realm;
    }

    public void add(Band band ){
        realm.executeTransactionAsync(thisRealm->{

                    Number nextID = thisRealm.where(Band.class).max("id");

                    if( nextID != null ){
                        band.setId( nextID.intValue() + 1 );
                    }else{
                        band.setId( 0 );
                    }

                    //Name must be unique..
                    RealmResults<Band> realmQuery = thisRealm.where( Band.class ).equalTo( "name", band.getName() ).findAll();
                    realmQuery.size();

                    if( realmQuery.size() == 0 ){
                        thisRealm.copyToRealm( band );
                    }else{

                        throw new IOException("Band name is not unique " + band.getName() );
                    }
        },
                ()->{
                    subject.onNext( band );
                },
                error->{
                    subject.onError( error );
                });
    }

    public Observable<Band> getObserver() {
        return subject.asObservable();
    }
}
