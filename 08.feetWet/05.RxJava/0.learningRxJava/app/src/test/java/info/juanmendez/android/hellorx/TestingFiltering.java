package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import info.juanmendez.android.hellorx.model.DataGenerator;
import info.juanmendez.android.hellorx.utils.ThreadUtils;
import info.juanmendez.android.hellorx.utils.TimeTicker;
import rx.Observable;

/**
 * Created by Juan on 9/18/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class TestingFiltering {

    @Test
    public void predicateTest(){

        writeComment("filter by predicate");
        Observable.from(DataGenerator.generateBigIntegerList()).filter(i -> {
            return ((i % 3 == 0) && (i < 20));
        }).subscribe(integer ->{

            System.out.println(integer);
        });
    }

    @Test
    public void filterByFirst(){

        writeComment("filter by first");
        Observable.from( DataGenerator.generateGreekAlphabet()).first().subscribe(s -> {
            System.out.println(s);
        });
    }

    @Test
    public void filterByLast(){

        writeComment("filter by last");
        Observable.from(DataGenerator.generateGreekAlphabet()).last().subscribe(s -> {
            System.out.println(s);
        });
    }


    @Test
    public void getFirstFour(){

        writeComment("filter by first four");
        Observable.from(DataGenerator.generateGreekAlphabet()).take(4).subscribe(s -> {
            System.out.println(s);
        });
    }

    @Test
    public void getEmpty(){

        writeComment("filter by empty");

        Observable.empty().firstOrDefault("List is empty").subscribe(o -> {

            System.out.println(o);
        });
    }

    @Test
    public void getEmpty2(){

        writeComment("filter by empty");

        Observable.empty().lastOrDefault("List is empty").subscribe(o ->{

            System.out.println( o );
        });
    }


    @Test
    public void testPredicateWithLast(){

        writeComment("preidcateWithLast");

        Observable.from(DataGenerator.generateGreekAlphabet()).lastOrDefault("No existe", s -> {
            return s.equals("Equis");
        }).subscribe(letter -> {
            System.out.println(letter);
        });
    }


    @Test
    public void testDistinct(){
            writeComment("working with distinct values");
        List<String> scrambled = DataGenerator.generateScrambledAndDuppedGreekAlphabet();

            Observable.from( scrambled ).distinct().subscribe(letter -> {
                System.out.println(letter + " @ " + scrambled.indexOf(letter));
            });

    }

    @Test
    public void testFirstLastPredicate(){

        writeComment("first and last predicate");

        // Emit the greek alphabet, but only the first letter
        // that matches our predicate
        Observable.from(DataGenerator.generateGreekAlphabet())
                .first( (letter) -> { return letter.equals( "Beta" ); } )
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();

        // Emit the greek alphabet, but only the last letter
        // that matches our predicate
        Observable.from(DataGenerator.generateGreekAlphabet())
                .last((letter) -> {
                    return letter.equals("Gamma");
                })
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

    }


    @Test
    public void testTimeBased(){

        TimeTicker ticker = new TimeTicker(10);
        ticker.start();


        try {
            // First, we get the observable event stream from the
            // ticker.
            ticker.toObservable()
                    // Next, we tell the observable to give us samples
                    // every one second.
                    .sample(1, TimeUnit.SECONDS)
                    .subscribe((t) -> {
                        // Every second, we will emit the current value
                        // of System.currentTimeMillis()
                        System.out.println("Tick: " + t);
                    });

            // We do this for 10 seconds...
            ThreadUtils.sleep(10000);
        } finally {
            // ...and then stop the ticker...which will also call
            // onCompleted() on all observers.
            ticker.stop();
        }
    }


    /**
     * In this example we set the observable to emit earlier than 5 seconds.
     * Then we hold the thread for 10 seconds so the ticker emits for that time and
     * then we set to pause the ticker for six seconds. In this way the observable is
     * caught on hold for longer than five seconds, and then the tiimeout error happens.
     */
    @Test
    public void testTimeout(){

        TimeTicker ticker = new TimeTicker(10);
        ticker.start();


        try {
            // First, we get the observable event stream from the
            // ticker.
            ticker.toObservable()
                    //if there is a timeout longer than five seconds, observable throws an error.
                    .timeout( 5, TimeUnit.SECONDS )
                    // Next, we tell the observable to give us samples
                    // every one second.
                    .sample(1, TimeUnit.SECONDS)
                    .subscribe((t) -> {
                        // Every second, we will emit the current value
                        // of System.currentTimeMillis()
                        System.out.println("Tick: " + t);
                    }, (e)->{
                        System.out.println( "timeout!");
                        e.printStackTrace();
                    });

            // We do this for 10 seconds...
            ThreadUtils.sleep(10000);
            ticker.pause();
            ThreadUtils.sleep( 6000 ); //so here there is a timeout to execute observable.timeOut

        } finally {
            // ...and then stop the ticker...which will also call
            // onCompleted() on all observers.
            ticker.stop();
        }
    }


    public static void writeComment( String comment ){
        System.out.println( "----------------------------------------------------");
        System.out.println( comment );
        System.out.println("----------------------------------------------------\n");
    }

}
