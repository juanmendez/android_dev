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


    public void testBehaviorSubjects(){
        BehaviorSubject<String> subject = BehaviorSubject.create("Start State");

    }

    public void testAsyncSubjects(){

        //AsyncSubjects only emits the last one before onComplete.
        AsyncSubject<String> subject = AsyncSubject.create();
    }
}
