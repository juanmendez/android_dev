package info.juanmendez.android.asynctask00;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * There is Android Annotation used, but it's off the topic.
 * Subject (listens to ) workingObservable @ doInBackground (line 46)
 * Subscription is a reference to Subject being subscribed to workingObservable.
 * upon cancel() (Activity calls on destroy) Subscription simply unsubscribes to stop workingObservable.
 * workingObservable only has a single subscription. Subject can have several subscriptions, though
 * Subject stops emitting and avoid memory leaks.
 *
 * Something which comes to mind is to keep list of subscriptions made to Subject in this class,
 * and therefore unsubscribe them as well upon cancel(). (more of this in further testing)
 */

@EBean
public class AsyncTasker
{
    @Bean ListAdapter adapter;
    PublishSubject subject;
    Subscription subscription;

    public AsyncTasker(){

        subject = PublishSubject.create();
    }

    public Observable<String> getObservable(){
        return subject.asObservable();
    }

    public void doInBackground( String[] stringArray ) {

        Scheduler scheduler = Schedulers.io();
        adapter.getList().clear();

        subscription = Observable.from(stringArray).map(s1 -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Logging.print("map@ " + Thread.currentThread().getName());
            adapter.getList().add(s1);
            return s1;
        })
        .subscribeOn(scheduler)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                s -> {
                    Logging.print("subscribe@ " + Thread.currentThread().getName());
                    subject.onNext(s);
                }, throwable -> {
                    Logging.print(throwable.getMessage());
                },
                () -> {
                    adapter.notifyDataSetChanged();
                    subject.onCompleted();
                }
        );
    }

    public void cancel(){

        if( subscription != null )
        {
            subscription.unsubscribe();
        }
    }
}