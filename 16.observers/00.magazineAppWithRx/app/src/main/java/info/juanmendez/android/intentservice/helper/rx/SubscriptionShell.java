package info.juanmendez.android.intentservice.helper.rx;


import android.os.Handler;
import android.os.Looper;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * Created by Juan on 11/28/2015.
 */
public class SubscriptionShell<T> {
    protected Subject subject;
    protected SubscriptionUI<T> subscriptionUI;
    private static final Handler mainThread = new Handler(Looper.getMainLooper());

    public SubscriptionShell( Subject subject ){
        this.subject = subject;
        subscriptionUI = new SubscriptionUI( subject );
    }

    public Subscription subscribe( Action1<T> observer ){
        return subscriptionUI.subscribe(observer);
    }

    public void unsubscribe( Subscription subscription ){
        subscriptionUI.unsubscribe(subscription);
    }

    public void unsubscribe( Action1<T> observer ){
        subscriptionUI.unsubscribe(observer);
    }

    public boolean hasListener(){
        return subscriptionUI.hasListener();
    }

    public Subject getSubject()
    {
        return subject;
    }

public void nextOnMain( T t ) {
    mainThread.post( ()->{
        subject.onNext( t );
    });
}
}
