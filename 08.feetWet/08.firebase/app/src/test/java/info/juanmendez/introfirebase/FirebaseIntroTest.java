package info.juanmendez.introfirebase;

import android.app.Application;

import com.firebase.client.Firebase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import info.juanmendez.introfirebase.model.User;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="src/main/AndroidManifest.xml", sdk = 21 )
public class FirebaseIntroTest {

    Application app;
    Firebase rootRef;

    static{
        ShadowLog.stream = System.out;
    }

    @Before
    public void prep(){
        app = RuntimeEnvironment.application;

        Firebase.setAndroidContext( app );
        rootRef = new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL);
    }

    @Test
    public void testAddingUser() throws Exception {

        System.out.println( "hello testing user");

        Firebase userRef = rootRef.child("users").child("alanis");
        User alanis = new User( "Alanis Morissette", 1974);
        userRef.setValue(alanis, (firebaseError, firebase) -> {
            if (firebaseError != null) {
                System.out.println("Data could not be saved. " + firebaseError.getMessage());
            } else {
                System.out.println("Data saved successfully.");
            }
        });
    }
}