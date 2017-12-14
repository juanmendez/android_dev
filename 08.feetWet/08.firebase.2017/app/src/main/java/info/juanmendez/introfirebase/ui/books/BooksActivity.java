package info.juanmendez.introfirebase.ui.books;

import android.content.Intent;
import android.databinding.Observable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import info.juanmendez.introfirebase.BR;
import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.databinding.ActivityBooksBinding;
import info.juanmendez.introfirebase.deps.Session;
import info.juanmendez.introfirebase.model.Book;
import info.juanmendez.introfirebase.model.BookFields;
import info.juanmendez.introfirebase.ui.book.BookFormActivity_;

/**
 * Created by juan on 12/8/17.
 */
@DataBound
@EActivity(R.layout.activity_books)
public class BooksActivity extends AppCompatActivity {

    @Bean
    Session mSession;

    @BindingObject
    ActivityBooksBinding mBinding;


    @ViewById(R.id.books_recylerview)
    RecyclerView mRecyclerView;

    private BooksAdapter mBooksAdapter;
    private Query mBookQuery;
    private ValueEventListener mValueEventListener;

    private Observable.OnPropertyChangedCallback mCallback;

    @AfterViews
    void afterViews(){
        mBinding.setViewModel( new BooksViewModel() );
    }

    @Override
    protected void onResume(){
        super.onResume();
        setUpView();
        loadBooks();
        observeBookList();
    }

    private void setUpView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mBooksAdapter = new BooksAdapter(getLayoutInflater(), mBinding.getViewModel());
        mRecyclerView.setAdapter( mBooksAdapter );
    }

    private void loadBooks(){
        mBookQuery = mSession.getDbReference().child(getString(R.string.tbl_books) ).child( mSession.getUI() );

        List<Book> books = new ArrayList<>();


        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot: dataSnapshot.getChildren() ){
                    Book book = snapshot.getValue(Book.class );
                    book.setKey( snapshot.getKey() );
                    books.add( book );
                }

                mBinding.getViewModel().setBooks( books );
                mBinding.getViewModel().isLoading.set(false );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mBookQuery.addValueEventListener( mValueEventListener);
    }

    private void observeBookList(){
        mBinding.getViewModel().addOnPropertyChangedCallback(mCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if( i == BR.bookPicked ){

                    //this could be refactored but for achieving demo, this is all done in here
                    Intent intent = new Intent(BooksActivity.this, BookFormActivity_.class );

                    if( mBinding.getViewModel().getBookPicked() != null ){
                        intent.putExtra(BookFields.KEY, mBinding.getViewModel().getBookPicked().getKey() );
                    }

                    BooksActivity.this.startActivity( intent );
                }
            }
        });
    }


    @Click(R.id.books_addBtn)
    public void onNewBook(){
        mBinding.getViewModel().setBookPicked(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBookQuery.removeEventListener( mValueEventListener );
        mBooksAdapter.disconnect();
        mBinding.getViewModel().removeOnPropertyChangedCallback( mCallback );
    }
}
