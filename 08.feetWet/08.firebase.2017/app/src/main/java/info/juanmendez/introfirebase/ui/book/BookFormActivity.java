package info.juanmendez.introfirebase.ui.book;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.databinding.ActivityBookFormBinding;
import info.juanmendez.introfirebase.deps.Books;
import info.juanmendez.introfirebase.deps.Session;
import info.juanmendez.introfirebase.model.Book;
import info.juanmendez.introfirebase.model.BookFields;
import info.juanmendez.introfirebase.ui.books.BooksActivity_;

/**
 * Created by juan on 12/8/17.
 */
@DataBound
@EActivity(R.layout.activity_book_form)
@OptionsMenu(R.menu.book_form_menu)
public class BookFormActivity extends AppCompatActivity {

    @BindingObject
    ActivityBookFormBinding mBinding;

    @Bean
    Session mSession;

    @Bean
    Books mBooks;

    @ViewById(R.id.book_form_coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    private BookViewModel mViewModel;

    @AfterViews
    void afterViews(){

        mViewModel = new BookViewModel();
        mBinding.setBookModel( mViewModel );
        mBinding.setView( this );

        Intent i = getIntent();
        String key = i.getStringExtra(BookFields.KEY);

        if( key != null ){
            mSession.getDbReference()
                    .child( getString(R.string.tbl_books ))
                    .child( mSession.getUI() )
                    .child( key )
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Book book = snapshot.getValue(Book.class );
                            book.setKey( snapshot.getKey() );
                            setTitle( "update: " + book.getTitle() );
                            mBooks.setBookEdited( book );
                            mViewModel.setBook( mBooks.getBookEdited() );
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }else{
            setTitle( "add new book" );
            mBooks.setBookEdited( new Book() );
            mViewModel.setBook( mBooks.getBookEdited() );
        }
    }

    public void saveBook(){

       Book thisBook = mViewModel.getBook();
       Task taskToFollow;


       if( thisBook.getKey() != null ){

           taskToFollow = mSession.getDbReference()
                   .child( getString(R.string.tbl_books ))
                   .child( mSession.getUI() )
                   .child( thisBook.getKey() )
                   .setValue( mViewModel.getBook());

       }else{

           taskToFollow = mSession.getDbReference()
                   .child( getString(R.string.tbl_books ))
                   .child( mSession.getUI() )
                   .push()
                   .setValue( mViewModel.getBook());
       }

        taskToFollow.addOnCompleteListener( task -> {
           Snackbar.make( mCoordinatorLayout, thisBook.getKey()!=null?"Book updated!":"Book added!", Snackbar.LENGTH_SHORT ).show();
       })
       .addOnFailureListener( e -> {
           Snackbar.make( mCoordinatorLayout, "Book error!", Snackbar.LENGTH_SHORT ).show();
       });
    }

    @OptionsItem(R.id.book_form_delete)
    void onBookRemoval(){
        if( mBooks.getBookEdited().getKey() != null ){

            mSession.getDbReference()
                    .child(getString(R.string.tbl_books))
                    .child( mSession.getUI() )
                    .child( mBooks.getBookEdited().getKey() )
                    .removeValue((databaseError, databaseReference) -> {
                        finish();
                    });

        }
    }
}
