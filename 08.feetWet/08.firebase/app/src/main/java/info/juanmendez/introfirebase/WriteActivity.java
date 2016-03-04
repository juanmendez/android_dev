package info.juanmendez.introfirebase;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.HashMap;
import java.util.Map;

import info.juanmendez.introfirebase.model.User;

@EActivity(R.layout.activity_main)
public class WriteActivity extends AppCompatActivity {

    Firebase rootRef;
    private static final String TAG = "Firebase";

    @AfterViews
    public void afterViews(){
        Firebase.setAndroidContext( getApplicationContext() );
        rootRef = new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL);
        savingWithTransaction();
        savingWithTransaction();
        savingWithTransaction();
        savingWithTransaction();
        savingWithTransaction();
    }


    void testAddingChild(){
        /**
         * custom objects
         */
        Firebase userRef = rootRef.child("users").child("alanis");
        User alanis = new User( "Alanis Morissette", 1974);
        userRef.setValue(alanis);
        userRef.child("birthYear").setValue(1912);
    }


    void testAddingChildWithHandler(){

        Firebase userRef = rootRef.child("users").child("alanis");
        User alanis = new User( "Alanis Morissette", 1974);
        userRef.setValue(alanis, (firebaseError, firebase) -> {
            if (firebaseError != null) {
                Log.i(TAG, "Data could not be saved. " + firebaseError.getMessage());
            } else {
                Log.i(TAG, "Data saved successfully.");
            }
        });
    }

    /**
     * this is a synchronized way to save data while
     * many users want to update at the same time.
     */
    void savingWithTransaction(){

        Firebase upvotesRef  = rootRef.child("votes");

        upvotesRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.i( TAG, "on complete");
            }
        });

    }

    void testAddingMappedChild(){
        /**
         * Mapping objects
         */
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put( "birthYear", "1974" );
        userMap.put("fullName", "Juan Mendez");

        Map<String, Map<String,String>> users = new HashMap<>();
        users.put("juan", userMap);
    }

    void testUpdateChildWithHash(){

        //lets update Juan..
        Firebase userRef = rootRef.child("users").child("juan");
        Map<String, Object> nickname = new HashMap<>();
        nickname.put("nickname", "Juan Loco");
        userRef.updateChildren(nickname);
    }



}
