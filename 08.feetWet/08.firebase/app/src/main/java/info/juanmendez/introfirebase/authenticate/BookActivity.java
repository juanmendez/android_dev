package info.juanmendez.introfirebase.authenticate;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import info.juanmendez.introfirebase.BuildConfig;
import info.juanmendez.introfirebase.MyApp;
import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.model.AuthPointer;
import info.juanmendez.introfirebase.model.Book;
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

    BookAdapter adapter;

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
                readBooks();
            }else{
                logoffReady();
            }
        });

        if( authPointer.getAuthData() != null ){
            readBooks();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        subscription.unsubscribe();

        if( adapter != null ){
            adapter.cleanup();
        }
    }

    private void addLoginForm(){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(android.R.id.content, EmailLoginForm_.builder().build(), "login_form").commit();

        if( menuLogOut != null )
            menuLogOut.setVisible(false);

        if( adapter != null ){
            adapter.cleanup();
            adapter = null;
        }
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
        addLoginForm();
    }

    void writeBooks(){

        Firebase authorBooks = new Firebase( BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/books/"  + authPointer.getAuthData().getUid() );
        Book b = new Book();
        b.setTitle( "It's so Easy: and other lies");
        b.setAuthor("Duff McKagan");
        b.setShopUrl("http://www.amazon.com/Its-So-Easy-other-lies/dp/1451606648/ref=sr_1_1?ie=UTF8&qid=1457236684&sr=8-1&keywords=duff+mckagan");
        authorBooks.push().setValue( b, (firebaseError, firebase) -> {
            if( firebaseError != null )
                Log.i("BookActivity", firebaseError.getMessage() );

        } );

        b = new Book();
        b.setTitle("How to Be a Man (and other illusions)");
        b.setAuthor("Duff McKagan, Chris Kornelis");
        b.setShopUrl("http://www.barnesandnoble.com/w/how-to-be-a-man-duff-mckagan/1119972606?ean=9780306823879");
        authorBooks.push().setValue(b, (firebaseError, firebase) -> {
            if( firebaseError != null )
                Log.i("BookActivity", firebaseError.getMessage() );
        });
    }

    void readBooks(){

        Firebase authorBooks = new Firebase( BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/books/"  + authPointer.getAuthData().getUid() );
        adapter = new BookAdapter(this, Book.class, R.layout.book_item, authorBooks);
        listView.setAdapter( adapter );
    }
}
