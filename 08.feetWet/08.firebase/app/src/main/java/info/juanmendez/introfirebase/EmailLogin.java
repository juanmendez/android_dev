package info.juanmendez.introfirebase;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_email_login)
public class EmailLogin extends AppCompatActivity {

    @ViewById
    EditText editTextEmail;

    @ViewById
    EditText editPassword;

    @ViewById
    Button submit;

    Firebase rootRef;
    private static final String TAG = "Firebase";

    @AfterViews
    public void afterViews(){
        Firebase.setAndroidContext(getApplicationContext());
        rootRef = new Firebase(BuildConfig.UNIQUE_FIREBASE_ROOT_URL);

        // Create a handler to handle the result of the authentication
        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.i( TAG, "authenticated");
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.i( TAG, "firebaseError" );
            }
        };

        submit.setOnClickListener(v -> {

            if( !editTextEmail.getText().toString().isEmpty() &&
                    !editPassword.getText().toString().isEmpty() ){

                Log.i(TAG, "submit");

                rootRef.authWithPassword( editTextEmail.getText().toString(), editPassword.getText().toString(), authResultHandler);
            }

        });

    }
}
