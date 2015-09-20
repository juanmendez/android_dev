package info.juanmendez.android.hellorx.subject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Juan on 9/18/2015.
 */
public class TimeTicker {

    BehaviorSubject<Long> tickerSubject;

    public TimeTicker(Long l )
    {

    }

    public Observable<Long> toObservable(){
        return tickerSubject;
    }

    public synchronized void start(){

    }

    public synchronized void stop(){

    }
}
