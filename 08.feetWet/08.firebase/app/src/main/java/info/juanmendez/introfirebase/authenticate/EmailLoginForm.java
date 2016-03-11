package info.juanmendez.introfirebase.authenticate;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import info.juanmendez.introfirebase.MyApp;
import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.model.AuthPointer;

/**
 * Created by Juan on 3/4/2016.
 */
@EFragment(R.layout.activity_email_login)
public class EmailLoginForm extends Fragment {

    @ViewById
    EditText editTextEmail;

    @ViewById
    EditText editPassword;

    @ViewById
    Button submit;

    @ViewById
    LoginButton fb_submit;

    @ViewById
    TextView textError;

    @App
    MyApp app;

    @Inject
    Firebase rootRef;

    @Inject
    AuthPointer authPointer;

    private static final String TAG = "Firebase";
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    private Firebase.AuthResultHandler authResultHandler;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @AfterViews
    public void afterViews(){
        MyApp.objectGraph(getActivity()).inject(this);

        authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                authPointer.setAuthData( authData );
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                textError.setVisibility(View.VISIBLE);
                textError.setText(firebaseError.getMessage());

                rootRef.unauth();
                LoginManager.getInstance().logOut();
            }
        };

        emailSetup();
        facebookSetup();
    }

    void emailSetup(){
        submit.setOnClickListener(v -> {

            if (!editTextEmail.getText().toString().isEmpty() &&
                    !editPassword.getText().toString().isEmpty()) {
                rootRef.authWithPassword(editTextEmail.getText().toString(), editPassword.getText().toString(), authResultHandler);
                textError.setVisibility(View.GONE);
            }
        });
    }

    void facebookSetup(){
        fb_submit.setReadPermissions("user_friends", "email");
        fb_submit.setFragment(this);

        fb_submit.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = loginResult.getAccessToken();

                if (token != null) {
                    rootRef.authWithOAuthToken("facebook", token.getToken(), authResultHandler);
                } else {
                    rootRef.unauth();
                }
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "fb login was canceled");
            }

            @Override
            public void onError(FacebookException error) {
                textError.setVisibility(View.VISIBLE);
                textError.setText(error.getMessage());
            }
        });

        LoginManager.getInstance().logOut();
    }
}
