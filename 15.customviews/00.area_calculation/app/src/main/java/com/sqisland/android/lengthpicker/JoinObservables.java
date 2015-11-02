package com.sqisland.android.lengthpicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * This class emits all lists of observable values whenever
 * any of them emits an update.
 * @param <T> All observables must emit the same type
 */
public class  JoinObservables<T> {

    /**
     * hashmap points to observable, and latest value emitted
     */
    HashMap<Observable<T>,T> map = new HashMap<>();
    BehaviorSubject<ArrayList<T>> subject;

    /**
     * subject emits a list of all observable values in the
     * same order they were first submitted @ constructor.
     */
    ArrayList<T> results = new ArrayList<>();

    JoinObservables( Observable<T>... observables ){
        subject = BehaviorSubject.create();

        for( Observable<T> observable: observables ){
            map.put(observable, null);

            observable.subscribe(t -> {
                map.put(observable, t);
                doNext();
            });
        }
    }

    /**
     * emit all observable values in same order given.
     */
    private void doNext(){

        results.clear();
        Map.Entry<Observable<T>,T > entry;
        Iterator it = map.entrySet().iterator();

        while( it.hasNext() )
        {
            entry = (Map.Entry<Observable<T>,T >) it.next();
            results.add( entry.getValue() );
        }

        subject.onNext( results );
    }

    public Observable<ArrayList<T>> asObservable(){
        return subject.asObservable();
    }
}
