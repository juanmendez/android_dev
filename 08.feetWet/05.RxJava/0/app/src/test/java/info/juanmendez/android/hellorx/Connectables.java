package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import info.juanmendez.android.hellorx.utils.ThreadUtils;
import info.juanmendez.android.hellorx.utils.TimeTicker;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by Juan on 9/25/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class Connectables {

    //@Test
    public void testConnectable101(){
        // Create a ticker that goes off every 1/2 second.
        TimeTicker ticker = new TimeTicker(500);
        ticker.start();

        // From the ticker, we create a connectable observable by
        // get the observable and then calling its "publish" method.
        ConnectableObservable<Long> connectable = ticker.toConnectable();

        // Next we do our subscription....
        connectable
                .subscribe(
                        (t) -> {
                            System.out.println("Tick: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );

        // But notice how for 3 seconds nothing happens until we call "connect"
        System.out.println("Sleeping for 3 seconds...");
        ThreadUtils.sleep(3000);

        // Now we call "connect" on the connectable observable...and things start happening...
        System.out.println("Connecting...");
        connectable.connect();

        // Let the ticker do its thing for 3 seconds so we can see events
        // flowing...
        ThreadUtils.sleep(3000);
        System.out.println("Three seconds are up!");

        // Stop the ticker and kill the example's VM
        ticker.stop();
    }


    //@Test
    public void testConnectableDual(){
        // Create a ticker that goes off every 1/2 second.
        TimeTicker ticker = new TimeTicker(500);
        ticker.start();

        // From the ticker, we create a connectable observable by
        // get the observable and then calling its "publish" method.
        ConnectableObservable<Long> connectable = ticker.toConnectable();

        // Next we do our subscription....
        connectable
                .subscribe(
                        (t) -> {
                            System.out.println("Tick: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );


        // ...and then another subscription!  This is new!  I thought you could
        // only do one?  You can do as many as necessary, even against a normal
        // observable.  ConnectableObservable gives you control over when the
        // underlying observable begins to emit events.
        connectable
                .subscribe(
                        (t) -> {
                            System.out.println("Tick2: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );

        // But notice how for 3 seconds nothing happens until we call "connect"
        System.out.println("Sleeping for 3 seconds...");
        ThreadUtils.sleep(3000);

        // Now we call "connect" on the connectable observable...and things start happening...
        System.out.println("Connecting...");
        connectable.connect();

        // Let the ticker do its thing for 3 seconds so we can see events
        // flowing...
        ThreadUtils.sleep(3000);
        System.out.println("Three seconds are up!");

        // Stop the ticker and kill the example's VM
        ticker.stop();
    }

    //@Test
    public void testDualMultiThreading(){
        // Create a ticker that goes off every 1/2 second.
        TimeTicker ticker = new TimeTicker(500);
        ticker.start();

        // From the ticker, we create a connectable observable by
        // get the observable and then calling its "publish" method.
        ConnectableObservable<Long> connectable = ticker.toConnectable();

        // Next we do our subscription....
        connectable
                // We want to run this on a different thread than the ticker thread
                .observeOn(Schedulers.computation())
                .subscribe(
                        (t) -> {
                            System.out.println("Tick: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );


        // ...and then another subscription!  This is new!  I thought you could
        // only do one?  You can do as many as necessary, even against a normal
        // observable.  ConnectableObservable gives you control over when the
        // underlying observable begins to emit events.
        connectable
                // We want to run this on a different thread than the ticker thread
                .observeOn(Schedulers.computation())
                .subscribe(
                        (t) -> {
                            System.out.println("Tick2: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );

        // But notice how for 3 seconds nothing happens until we call "connect"
        System.out.println("Sleeping for 3 seconds...");
        ThreadUtils.sleep(3000);

        // Now we call "connect" on the connectable observable...and things start happening...
        System.out.println("Connecting...");
        connectable.connect();

        // Let the ticker do its thing for 3 seconds so we can see events
        // flowing...
        ThreadUtils.sleep(3000);
        System.out.println("Three seconds are up!");

        // Stop the ticker and kill the example's VM
        ticker.stop();
    }


    @Test
    public void testSlownessOnSubscriber(){
        // Create a ticker that goes off every 1/2 second.
        TimeTicker ticker = new TimeTicker(500);
        ticker.start();

        // From the ticker, we create a connectable observable by
        // get the observable and then calling its "publish" method.
        ConnectableObservable<Long> connectable = ticker.toConnectable();

        // Next we do our subscription....
        connectable
                // We want to run this on a different thread than the ticker thread
                .observeOn(Schedulers.computation())
                .subscribe(
                        (t) -> {
                            System.out.println("Tick: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );


        // ...and then another subscription!  This is new!  I thought you could
        // only do one?  You can do as many as necessary, even against a normal
        // observable.  ConnectableObservable gives you control over when the
        // underlying observable begins to emit events.
        connectable
                // We want to run this on a different thread than the ticker thread
                .observeOn(Schedulers.computation())
                .subscribe(
                        (t) -> {
                            ThreadUtils.sleep(4000);
                            System.out.println("Tick2: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );


        // Now we call "connect" on the connectable observable...and things start happening...
        System.out.println("Connecting...");
        connectable.connect();

        // Let the ticker do its thing for 3 seconds so we can see events
        // flowing...
        ticker.pause();
        ThreadUtils.sleep(1000);
        ticker.unpause();
        ThreadUtils.sleep(5000);



        System.out.println("Three seconds are up!");

        // Stop the ticker and kill the example's VM
        ticker.stop();
    }
}
