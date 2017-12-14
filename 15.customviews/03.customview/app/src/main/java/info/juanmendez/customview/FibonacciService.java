package info.juanmendez.android.reviewservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Juan Mendez on 3/24/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 *
 * @see https://github.com/juanmendez/reviewing-services/blob/00.simple_service/app/src/main/java/info/juanmendez/android/reviewservices/services/FibonacciService.java
 */

public class FibonacciService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int febCount = intent.getIntExtra("value", 0);

        /**
         * This message showed up when I tried instead to run callable in mainThread
         * 03-24 23:56:53.334 3182-3192/info.juanmendez.android.reviewservices I/art: Background partial concurrent mark sweep GC freed 46(1776B) AllocSpace objects, 64(13MB) LOS objects, 39% free, 5MB/9MB, paused 6.469ms total 58.319ms
         */
        if( febCount > 0 ){

            Flowable.fromCallable(() -> {
                Log.i("FibonacciService", "running in " + Thread.currentThread().getName() );
                String result = "";

                // See more at: http://www.java2novice.com/java-interview-programs/fibonacci-series/

                int[] feb = new int[febCount];
                feb[0] = 0;
                feb[1] = 1;
                for(int i=2; i < febCount; i++){
                    feb[i] = feb[i-1] + feb[i-2];
                }

                for(int i=0; i< febCount; i++){
                    result +=  feb[i] + " ";
                }

                Thread.sleep(2000);
                return result;
            }).subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(febResult -> {
                        Log.i("FibonacciService", "running in " + Thread.currentThread().getName() );
                        Log.i("FibonacciService", "result is " + febResult );
                    });
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i( "FibonacciService", "good bye!" );
        super.onDestroy();
    }
}
