package info.juanmendez.introfirebase.authenticate;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.client.Firebase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import info.juanmendez.introfirebase.BuildConfig;
import info.juanmendez.introfirebase.MyApp;
import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.model.AuthPointer;
import info.juanmendez.introfirebase.model.Book;

/**
 * Created by Juan on 3/11/2016.
 */

@EFragment(R.layout.book_list)
public class BookList extends Fragment {

    @ViewById
    ListView listView;

    @OptionsMenuItem
    MenuItem menuAddBook;

    @Inject
    AuthPointer authPointer;
    BookAdapter adapter;
    public static final String TAG = "BookList";
    private Firebase authorBooks;
    private int position = -1;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if( menuAddBook != null ){
            menuAddBook.setVisible(true);

            menuAddBook.setOnMenuItemClickListener(item -> {
               AddBookDialog addBookDialog = AddBookDialog.newInstance();
                addBookDialog.asObservable().subscribe(book -> {
                    writeBook( book );
                }, throwable -> {

                });
                addBookDialog.show(getFragmentManager(), "add book");
                return true;
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setRetainInstance(true);
    }

    @AfterViews
    public void afterViews(){
        MyApp.objectGraph(getActivity()).inject(this);
        authorBooks = new Firebase( BuildConfig.UNIQUE_FIREBASE_ROOT_URL + "/books/"  + authPointer.getAuthData().getUid() );
        authorBooks.keepSynced(true);
        readBooks();

        if( menuAddBook != null ) {
            menuAddBook.setVisible(true);
        }
    }

    @ItemLongClick
    public void listViewItemLongClicked( int position ){

        if( position != ListView.INVALID_POSITION ){

            Book bookSelected = adapter.getItem(position);

            DeleteBookDialog dialogFragment = DeleteBookDialog.newInstance(bookSelected);

            dialogFragment.asObservable().subscribe(aBoolean -> {
                if( aBoolean ){
                    Firebase bookItemRef = adapter.getRef( position );
                    bookItemRef.setValue(null);
                }
            });

            dialogFragment.show( getFragmentManager(), "delete book");
        }
    }

    @ItemClick
    public void listViewItemClicked( Book book ){
        Intent i = new Intent( Intent.ACTION_VIEW );
        i.setData( Uri.parse(book.getShopUrl()));
        startActivity( i );
    }

    void readBooks(){
        adapter = new BookAdapter(this.getActivity(), Book.class, R.layout.book_item, authorBooks);
        listView.setAdapter(adapter);
    }

    void writeBook( Book book ){
        authorBooks.push().setValue( book, (firebaseError, firebase) -> {
            if( firebaseError != null )
                Log.i("BookActivity", firebaseError.getMessage());
        } );
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.cleanup();

        if( menuAddBook != null )
            menuAddBook.setVisible(false);
    }
}
