package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Juan on 9/18/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class SimpleCreation {

    static{
        ShadowLog.stream = System.out;
    }

    @Test
    public void simplestObservable(){
        Observable<Integer> observable = null;
        //create observable from a single value

        observable = Observable.just(42);
        writeComment("observe integer");
        observable.subscribe(i -> {
            System.out.println(i);
        });

        writeComment("observe list");
        observable = Observable.from(generateFibonnacciList());
        observable.subscribe(integer -> {
            System.out.println(integer);
        });
    }

    @Test
    public void observeFromFuture() {

        writeComment( "observing a future object");
        FutureTask<List<Integer>> future = new FutureTask<List<Integer>>(() -> {
            return generateFibonnacciList();
        });

        Observable<List<Integer>> observable = Observable.from(future);

        //you could just execute the future but instead we wrapp it in a scheduler
        //which will do the computation processing.
        Schedulers.computation().createWorker().schedule(() -> {
            future.run();
        });

        observable.subscribe(integers -> {
            for (Integer integer : integers) {
                System.out.println( integer );
            }
        });
    }

    @Test
    public void findTheThreads(){

        writeComment("working on main thread");
        Observable<Integer> observable = Observable.from(generateFibonnacciList());
        observable.subscribe((i) -> {
                    writeThread(Thread.currentThread().getName());
                    System.out.println(i);
                    writeThread(Thread.currentThread().getName());
                },
                (t) -> {
                    t.printStackTrace();
                },
                () -> {
                    System.out.println("completed!!");
                });


        writeComment("work on another thread");

        observable = Observable.from(generateFibonnacciList());
        observable.subscribeOn(Schedulers.newThread())
                .subscribe((i) -> {
                            writeThread(Thread.currentThread().getName());
                            System.out.println(i);
                            writeThread(Thread.currentThread().getName());
                        },
                        (t) -> {
                            t.printStackTrace();
                        },
                        () -> {
                            System.out.println("completed!!");
                        });


        writeComment("work on io/newThread");

        observable = Observable.from(generateFibonnacciList());
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe((i) -> {
                            writeThread(Thread.currentThread().getName());
                            System.out.println(i);
                            writeThread(Thread.currentThread().getName());
                        },
                        (t) -> {
                            t.printStackTrace();
                        },
                        () -> {
                            System.out.println("completed!!");
                        });


    }

    public static List<Integer> generateFibonnacciList(){
        ArrayList<Integer>  returnList = new ArrayList<Integer>();

        int times = 0;
        int fibo = 1;

        do{
            if( times <= 1 )
                fibo = times;
            else
                fibo = returnList.get(times-1) + returnList.get(times - 2);

            returnList.add( fibo );

            times++;
        }while( times < 20 );


        return returnList;
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
