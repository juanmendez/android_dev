package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import info.juanmendez.android.hellorx.model.DataGenerator;
import info.juanmendez.android.hellorx.utils.ThreadUtils;
import info.juanmendez.android.hellorx.utils.TimedEventSequence;
import rx.Observable;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 9/25/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class RaiseConditions {

    @Test
    public void testAmbiguous(){
// "Ambiguous" example - First, we create two TimedEventSequence
        // instances...one that emits Greek letters with a shorter interval,
        // the other that emits English letters on a longer interval.
        TimedEventSequence<String> sequence1 = new TimedEventSequence<String>(DataGenerator.generateGreekAlphabet(), 50);
        TimedEventSequence<String> sequence2 = new TimedEventSequence<String>(DataGenerator.generateEnglishAlphabet(), 100);

        // Create an "ambiguous" observable from the two sequences
        Observable.amb(sequence1.toObservable(), sequence2.toObservable())
                .subscribe((s) -> {
                    System.out.println(s);
                });


        // Start the driver thread for each of these sequences.
        sequence1.start();
        sequence2.start();

        // Wait for 4 seconds while things run...we should see Greek letters
        // being emitted at 50ms intervals for 4 seconds since the first
        // sequence to emit events will be selected by the amb operator.
        ThreadUtils.sleep(4000);

        // Stop each sequence.
        sequence1.stop();
        sequence2.stop();
    }

    public static void writeComment( String comment ){
        System.out.println( "----------------------------------------------------");
        System.out.println( comment );
        System.out.println("----------------------------------------------------\n");
    }
}
