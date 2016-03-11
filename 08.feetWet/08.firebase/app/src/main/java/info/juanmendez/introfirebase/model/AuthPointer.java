package info.juanmendez.introfirebase.model;

import com.firebase.client.AuthData;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 3/4/2016.
 */
public class AuthPointer {
    AuthData authData;
    PublishSubject<String> subject;

    public AuthPointer(){
        subject = PublishSubject.create();
    }

    public Observable<String> asObservable(){
        return subject.asObservable();
    }

    public AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;

        if( this.authData != null ){
            subject.onNext( this.authData.getUid() );
        }
        else
        {
            subject.onNext( null );
        }
    }
}