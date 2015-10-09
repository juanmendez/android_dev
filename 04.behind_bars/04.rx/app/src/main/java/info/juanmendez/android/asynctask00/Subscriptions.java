package info.juanmendez.android.asynctask00;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Juan on 10/8/2015.
 *
 * This class takes care of subscribing many to a single observable.
 * Upon call to unsubscribe it either unsubscribes one or many.
 */
public class Subscriptions<T>
{
    HashMap<Observer<T>, Subscription > subscriptionMap = new HashMap<>();
    Observable<T> subject;

    public Subscriptions(Observable<T> observable)
    {
        this.subject = observable;
    }

    public Subscription subscribe( Observer<T> observer )
    {
        if( subscriptionMap.get(observer) == null )
        {
            Subscription subscription = subject.subscribe(observer);
            subscriptionMap.put( observer, subscription );

            return subscription;
        }

        return null;
    }

    public void unsubscribe(){
        Subscription subscription;
        Map.Entry<Observer<T>, Subscription> entry;

        for( Iterator it = subscriptionMap.entrySet().iterator(); it.hasNext(); )
        {
            entry = (Map.Entry<Observer<T>, Subscription>) it.next();
            subscription = entry.getValue();
            subscription.unsubscribe();
            it.remove();
        }
    }

    public void unsubscribe( Observer<T> observer ){

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

            Map.Entry<Observer<T>, Subscription> entry;

            for( Iterator it = subscriptionMap.entrySet().iterator(); it.hasNext(); )
            {
                entry = (Map.Entry<Observer<T>, Subscription>) it.next();

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
