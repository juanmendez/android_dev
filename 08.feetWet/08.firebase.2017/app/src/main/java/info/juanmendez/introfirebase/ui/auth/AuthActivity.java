package info.juanmendez.introfirebase.ui.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EActivity;

import java.util.Arrays;
import java.util.List;

import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.databinding.ActivityAuthBinding;
import info.juanmendez.introfirebase.deps.Books;
import info.juanmendez.introfirebase.deps.Session;
import info.juanmendez.introfirebase.model.Book;
import info.juanmendez.introfirebase.ui.book.BookFormActivity_;

@DataBound
@EActivity(R.layout.activity_auth)
public class AuthActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @BindingObject
    ActivityAuthBinding mAuthViewModel;

    @Bean
    Session mSession;

    @Bean
    Books mBooks;

    AuthViewModel viewModel;

    @AfterViews
    void afterViews(){
        viewModel = new AuthViewModel();
        viewModel.loggedIn.set( mSession.isLoggedIn() );
        mAuthViewModel.setAuthViewModel( viewModel );
        mAuthViewModel.setView( this );
    }

    //lets start with authentication..
    public void fireEventClick(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);


    }

    public void logout(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    mSession.setLoggedIn( false );
                    viewModel.loggedIn.set( mSession.isLoggedIn() );
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == ResultCodes.OK) {
                // Successfully signed in
                mSession.setUser(FirebaseAuth.getInstance().getCurrentUser());
                mSession.setLoggedIn( true );

                mBooks.setBookEdited( new Book() );
                Intent intent = new Intent(this, BookFormActivity_.class );
                startActivity( intent );
                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
                mSession.setLoggedIn( false );
            }

            viewModel.loggedIn.set( mSession.isLoggedIn() );
        }
    }


}
