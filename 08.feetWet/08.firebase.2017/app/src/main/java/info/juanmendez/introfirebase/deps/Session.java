package info.juanmendez.introfirebase.deps;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.EBean;

/**
 * Created by juan on 12/8/17.
 *
 * This singleton shortens firebase references
 */
@EBean(scope = EBean.Scope.Singleton)
public class Session {
    private boolean mIsLoggedIn;
    private FirebaseUser mUser;
    private FirebaseApp mFirebaseApp;
    private FirebaseDatabase mDatabase;


    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        mIsLoggedIn = loggedIn;

        if( !loggedIn ){
            mUser = null;
            mFirebaseApp = null;
            mDatabase = null;
        }
    }

    public FirebaseUser getUser() {
        return mUser;
    }

    public void setUser(FirebaseUser user) {
        mUser = user;
    }

    public DatabaseReference getDbReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public String getUI(){
        if( getUser() != null ){
            return getUser().getUid();
        }else{
            return "";
        }
    }
}
