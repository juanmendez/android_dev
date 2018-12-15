package info.juanmendez.introfirebase.authenticate;

import android.app.DialogFragment;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 3/12/2016.
 */
public class RxDialogFragment<T> extends DialogFragment{

    private PublishSubject<T> subject;

    public RxDialogFragment(){
        super();
        subject = PublishSubject.create();
    }

    public Observable<T> asObservable(){
        return subject.asObservable();
    }

    protected void onNext( T t ){
        subject.onNext( t );
    }

    protected void onError( Throwable throwable ){
        subject.onError(throwable);
    }

    protected void onCompleted(){
        subject.onCompleted();
    }
}