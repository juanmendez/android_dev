package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import info.juanmendez.android.hellorx.model.DataGenerator;
import rx.Observable;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 9/25/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class ConditionalOperations {

    @Test
    public void testWithEmpty()
    {
        writeComment("test with empty");

        // defaultIfEmpty example - We create an empty observable
        // and then apply "defaultIfEmpty" and set the default to "Hello World".
        // Since the observable is empty, "Hello World" will be emitted as
        // the only event.
        Observable.empty()
                .defaultIfEmpty("Hello World")
                .subscribe((s) -> {
                    System.out.println(s);
                });

    }

    @Test
    public void testIfEmptyForFirst(){
        writeComment("test empty if first");

        // defaultIfEmpty example  2 - We create an non-empty observable
        // and then apply "defaultIfEmpty" and set the default to "Hello World".
        // Since the observable is not empty, the list items will be emitted.
        Observable.from(DataGenerator.generateGreekAlphabet())
                .defaultIfEmpty("Hello World")
                .first() // we just want to show that it isn't Hello World...
                .subscribe((s) -> {
                    System.out.println(s);
                });
    }



    @Test
    public void testSkipWhile(){
        writeComment("testSkipWhile");

        Observable.from(DataGenerator.generateFibonacciList())
                .skipWhile((i) -> {
                    return i < 8;
                })
                .subscribe((i) -> {
                    System.out.println(i);
                });

    }

    @Test
    public void testTakeWhile(){
        writeComment("testTakeWhile");

        Observable.from(DataGenerator.generateFibonacciList())
                .takeWhile((i) -> {
                    return i < 8;
                })
                .subscribe((i) -> {
                    System.out.println(i);
                });
    }

    @Test
    public void testTakeWhileWithIndex(){
        writeComment("takeWhileWithIndex");

        Observable.from(DataGenerator.generateFibonacciList()).takeUntil(integer -> {
            return integer > 50;
        }).subscribe(integer1 -> {
            System.out.println( integer1);
        });

    }

    public static void writeComment( String comment ){
        System.out.println( "----------------------------------------------------");
        System.out.println( comment );
        System.out.println("----------------------------------------------------\n");
    }
}
