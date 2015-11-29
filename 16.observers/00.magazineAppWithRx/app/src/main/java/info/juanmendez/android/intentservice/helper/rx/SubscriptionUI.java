package info.juanmendez.android.intentservice.helper.rx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Juan on 11/28/2015.
 */
public class SubscriptionUI<T> {

    HashMap<Action1<T>, Subscription > subscriptionMap = new HashMap<Action1<T>, Subscription>();
    Observable<T> subject;

    public SubscriptionUI(Observable<T> observable)
    {
        this.subject = observable;
    }

    public boolean hasListener(){
        return subscriptionMap.size() > 0;
    }

    public Subscription subscribe( Action1<T> observer )
    {
        if( subscriptionMap.get(observer) == null )
        {
            Subscription subscription = subject.subscribe(observer);
            subscriptionMap.put( observer, subscription );

            return subscription;
        }

        return null;
    }

    public void unsubscribeAll(){
        Subscription subscription;
        Map.Entry<Action1<T>, Subscription> entry;

        for( Iterator it = subscriptionMap.entrySet().iterator(); it.hasNext(); )
        {
            entry = (Map.Entry<Action1<T>, Subscription>) it.next();
            subscription = entry.getValue();
            subscription.unsubscribe();
            it.remove();
        }
    }

    public void unsubscribe( Action1<T> observer ){

        Subscription subscription;

        if( subscriptionMap.get(observer) == null )
        {
            subscription = subscriptionMap.get(observer);
            subscription.unsubscribe();

            subscriptionMap.remove( observer );
        }
    }

    public void unsubscribe( Subscription subscription ){

        if( subscriptionMap.containsValue(subscription)){

            Map.Entry<Action1<T>, Subscription> entry;

            for( Iterator it = subscriptionMap.entrySet().iterator(); it.hasNext(); )
            {
                entry = (Map.Entry<Action1<T>, Subscription>) it.next();

                if( subscription == entry.getValue() )
                {
                    subscription.unsubscribe();
                    it.remove();
                    break;
                }
            }
        }
    }
}