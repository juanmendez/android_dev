package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.List;
import java.util.concurrent.FutureTask;

import info.juanmendez.android.hellorx.model.DataGenerator;
import info.juanmendez.android.hellorx.utils.ThreadUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Juan on 9/18/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class workingWithObservables {

    static{
        ShadowLog.stream = System.out;
    }

    @Test
    public void simplestObservable(){
        Observable<Integer> observable = null;
        //create observable from a single value

        //current code executed in current thread, due to lack of scheduler..

        observable = Observable.just(42);
        writeComment("observe integer");
        observable.subscribe(i -> {
            System.out.println(i);
        });

        writeComment("observe list");
        observable = Observable.from(DataGenerator.generateFibonacciArray());
        observable.subscribe(integer -> {
            System.out.println(integer);
        });
    }

    @Test
    public void observeFromFuture() {

        writeComment( "observing a future object");
        FutureTask<List<Integer>> future = new FutureTask<List<Integer>>(() -> {
            return DataGenerator.generateFibonacciList();
        });

        Observable<List<Integer>> observable = Observable.from(future);

        //you could just execute the future but instead we wrap it in a scheduler
        //which will do the computation processing.
        Schedulers.computation().createWorker().schedule(() -> {
            future.run();
        });

        observable.subscribe(integers -> {
            for (Integer integer : integers) {
                System.out.println(integer);
            }
        });
    }

    @Test
    public void findTheThreads(){

        writeComment("working on main thread");
        Observable<Integer> observable = Observable.from(DataGenerator.generateFibonacciList());
        observable.subscribe((i) -> {
                    writeThread(ThreadUtils.currentThreadName());
                    System.out.println(i);
                    writeThread(ThreadUtils.currentThreadName());
                },
                (t) -> {
                    t.printStackTrace();
                },
                () -> {
                    System.out.println("completed!!");
                });


        writeComment("work on another thread");

        //the observable runs on another thread, the observer the same way as
        //we haven't specified its thread.
        observable = Observable.from(DataGenerator.generateFibonacciList());
        observable.subscribeOn(Schedulers.newThread())
                .subscribe((i) -> {
                            writeThread(ThreadUtils.currentThreadName());
                            System.out.println(i);
                            writeThread(ThreadUtils.currentThreadName());
                        },
                        (t) -> {
                            t.printStackTrace();
                        },
                        () -> {
                            System.out.println("completed!!");
                        });


        writeComment("work on io/newThread");

        observable = Observable.from(DataGenerator.generateFibonacciList());
        observable.observeOn(Schedulers.io())
                .subscribe((i) -> {
                            System.out.println(ThreadUtils.currentThreadName() + ": " + i);
                        },
                        (t) -> {
                            t.printStackTrace();
                        },
                        () -> {
                            System.out.println("completed!!");
                        });


    }

    @Test
    public void doFiltering(){

        writeComment("do filtering");
        Observable<Integer> observable = Observable.from(DataGenerator.generateBigIntegerList());

        observable.subscribeOn(Schedulers.newThread()).filter(integer -> {
            return integer % 2 == 0;
        }).doOnNext(xx -> {
            System.out.println("parallel thread in " + ThreadUtils.currentThreadName());
            ThreadUtils.sleep(10);
            System.out.println("parallel thread out " + ThreadUtils.currentThreadName());
        }).subscribe(i -> {
            System.out.println("next thread " + ThreadUtils.currentThreadName());
            System.out.println(i);
            System.out.println( "next thread exit " + ThreadUtils.currentThreadName() );
        }, t -> {
        }, () -> {
        });
    }

    public static void writeComment( String comment ){
        System.out.println( "----------------------------------------------------");
        System.out.println( comment );
        System.out.println( "----------------------------------------------------\n");
    }

    public static void writeThread( String name ){
        System.out.println( "[Thread: " + name + " ]");
    }

}
