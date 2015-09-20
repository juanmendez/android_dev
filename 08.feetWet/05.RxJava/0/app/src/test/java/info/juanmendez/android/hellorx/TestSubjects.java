package info.juanmendez.android.hellorx;

import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Three type sof subjects
 * publish, behavoir and async subjects
 */
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
