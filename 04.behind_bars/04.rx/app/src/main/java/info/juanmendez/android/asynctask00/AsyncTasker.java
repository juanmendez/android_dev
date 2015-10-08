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
 * Created by Juan on 5/11/2015.
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