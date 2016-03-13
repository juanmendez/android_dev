package info.juanmendez.introfirebase.authenticate;

import android.app.FragmentManager;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.client.Firebase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import info.juanmendez.introfirebase.MyApp;
import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.model.AuthPointer;
import rx.Subscription;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.login_menu)
public class BookActivity extends AppCompatActivity {

    @Inject
    AuthPointer authPointer;

    @Inject
    Firebase rootRef;

    @OptionsMenuItem
    MenuItem menuLogOut;

    Subscription subscription;

    @ViewById
    ListView listView;

    @AfterViews
    void afterViews(){

        MyApp.objectGraph(this).inject(this);

        if( authPointer.getAuthData() == null ){
            addLoginForm();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if( authPointer.getAuthData() != null ){
            logoffReady();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume(){
        super.onResume();


        subscription = authPointer.asObservable().subscribe( s -> {
            if( s != null ){
                removeLoginForm();
                logoffReady();
                //writeBooks();
                showBooks();
            }else{
                addLoginForm();
            }
        });

        if( authPointer.getAuthData() != null ){
            showBooks();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        subscription.unsubscribe();
    }

    private void addLoginForm(){
        FragmentManager fm = getFragmentManager();

        Fragment f = fm.findFragmentByTag("login_form");

        if( f == null )
            f = EmailLoginForm_.builder().build();

        fm.beginTransaction().replace(android.R.id.content, f, "login_form").commit();

        if( menuLogOut != null )
            menuLogOut.setVisible(false);
    }

    private void removeLoginForm(){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().remove(fm.findFragmentByTag("login_form")).commit();
    }

    private void logoffReady(){

        if( menuLogOut != null ){
            menuLogOut.setVisible(true);
        }
    }

    @OptionsItem(R.id.menuLogOut)
    void onLogOff(){

        rootRef.unauth();
        authPointer.setAuthData( null );
    }

    void showBooks(){
        FragmentManager fm = getFragmentManager();

        Fragment f = fm.findFragmentByTag("book_list");

        if( f == null )
           f = BookList_.builder().build();

        fm.beginTransaction().replace(android.R.id.content, f, "book_list").commit();
    }
}
