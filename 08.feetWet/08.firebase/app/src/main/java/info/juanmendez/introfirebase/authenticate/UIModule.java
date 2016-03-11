package info.juanmendez.introfirebase.authenticate;

import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.introfirebase.BuildConfig;
import info.juanmendez.introfirebase.MyApp;
import info.juanmendez.introfirebase.model.AuthPointer;

/**
 * Created by Juan on 3/4/2016.
 */

@Module(
        injects = {
                EmailLoginForm_.class,
                BookActivity_.class
        },
        complete = false,
        library = true
)
public class UIModule {

    private final MyApp application;

    @Provides
    @Singleton
    MyApp providesApplication(){
        return application;
    }

    @Provides
    @Singleton
    AuthPointer providesAuth(){
        return new AuthPointer();
    }

    @Provides
    @Singleton
    Firebase providesRootFirebase(){
        return new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL);
    }

    public UIModule(MyApp application){
        this.application = application;
    }
}
