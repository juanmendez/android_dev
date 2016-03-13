package info.juanmendez.introfirebase.authenticate;

import android.app.DialogFragment;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 3/12/2016.
 */
public class RxDialogFragment<T> extends DialogFragment {

    protected PublishSubject<T> subject;

    public RxDialogFragment(){
        super();
        subject = PublishSubject.create();
    }

    public Observable<T> asObservable(){
        return subject.asObservable();
    }
}
