package info.juanmendez.android.recyclerview.rx;

import info.juanmendez.android.recyclerview.model.Country;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 10/25/2015.
 */
public class UIObservable
{
    PublishSubject subject;
    SubscriptionHandler handler;

    public UIObservable(){
        subject = PublishSubject.create();
        handler = new SubscriptionHandler( subject );
    }

    public void emit( Country country ){

        if( country != null ){
            subject.onNext( country );
        }
    }

    public Subscription subscribe( Action1<Country> observer ){
        return handler.subscribe( observer );
    }

    public void unsubscribe( Subscription subscription ){
        handler.unsubscribe( subscription );
    }
}
