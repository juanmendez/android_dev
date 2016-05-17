package info.juanmendez.introfirebase;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;

import org.androidannotations.annotations.EApplication;

import dagger.ObjectGraph;
import info.juanmendez.introfirebase.authenticate.UIModule;

/**
 * Created by Juan on 3/4/2016.
 */
@EApplication
public class MyApp extends Application {

    private ObjectGraph objectGraph;

    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        FacebookSdk.sdkInitialize(this);
        objectGraph = ObjectGraph.create( new UIModule(this) );
    }

    public static ObjectGraph objectGraph( Context context ){
        return ((MyApp) context.getApplicationContext()).objectGraph;
    }
}
