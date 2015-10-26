package info.juanmendez.android.hellorx;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Three type sof subjects
 * publish, behavior and async subjects
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class TestSubjects
{

    /**
     * publishSubjects are good for multicasting events to subscribiers.
     * the subscribers will see only the events that happen AFTER they subscribe.
     *
     * Create a Publish Subject using its factory method.
     */
    public void testPublishSubjects()
    {
        PublishSubject<String> subject = PublishSubject.create();
    }


    /**
     * BehaviorSubject works like PublishSubject except it only receives the previous emit and the following remaining.
     * If it observes after complete, it will get that event.
     */
    public void testBehaviorSubjects(){
        BehaviorSubject<String> subject = BehaviorSubject.create("Start State");

    }

    /**
     * Subject that publishes only the last item observed to each Observer that has subscribed, when the source Observable completes.
     */
    public void testAsyncSubjects(){

        //AsyncSubjects only emits the last one before onComplete.
        AsyncSubject<String> subject = AsyncSubject.create();
    }
}
