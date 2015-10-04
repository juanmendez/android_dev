package info.juanmendez.android.hellorx;

import org.junit.Test;

import info.juanmendez.android.hellorx.db.ConnectionSubscription;
import info.juanmendez.android.hellorx.utils.ThreadUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by Juan on 9/26/2015.
 */
public class DBTest {

    @Test
    public void testFakeJDBC(){

        // Create a simple error handler that will emit the stack trace for a given exception
        Action1<Throwable> simpleErrorHandler = (t) -> {
            t.printStackTrace();
        };

        // Resource Factory function to create a TestDatabase connection and wrap it
        // in a ConnectionSubscription
        Func0<ConnectionSubscription> resourceFactory = () -> {
            return new ConnectionSubscription();
        };

        // Observable Factory function to create the resultset that we want.
        Func1<ConnectionSubscription, Observable<String>> greekAlphabetList = (connectionSubscription) -> {
            return Observable.from( connectionSubscription.getLetters() );
        };

        Observable<String> t = Observable.using(resourceFactory, greekAlphabetList, subscription -> {

            System.out.println( "time to say goodbye!");
            subscription.unsubscribe();
        });


        t.subscribe(
                (letter) -> {
                    System.out.println(ThreadUtils.currentThreadName() + " - " + letter);
                },
                simpleErrorHandler,
                () -> {
                    System.out.println( "onCompleted" );
                }
        );
    }
}
