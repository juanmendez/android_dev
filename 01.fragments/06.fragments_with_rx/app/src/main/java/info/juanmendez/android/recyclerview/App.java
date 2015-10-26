package info.juanmendez.android.recyclerview;

import android.app.Application;

import info.juanmendez.android.recyclerview.rx.UIObservable;

/**
 * Created by Juan on 10/25/2015.
 */
public class App extends Application
{
    private static UIObservable observable;


    public static UIObservable getObservable(){

        if( observable == null  )
        {
            observable = new UIObservable();
        }

        return observable;
    }
}
