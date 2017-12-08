package info.juanmendez.introfirebase.ui.book;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.BindingObject;
import org.androidannotations.annotations.DataBound;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.databinding.ActivityBookFormBinding;
import info.juanmendez.introfirebase.deps.Books;
import info.juanmendez.introfirebase.deps.Session;

/**
 * Created by juan on 12/8/17.
 */
@DataBound
@EActivity(R.layout.activity_book_form)
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
        mViewModel.setBook( mBooks.getBookEdited() );
        mBinding.setBookModel( mViewModel );
        mBinding.setView( this );
    }

    public void saveBook(){
        mSession.getDbReference()
                .child( getString(R.string.tbl_books ))
                .child( mSession.getUI() )
                .push()
                .setValue( mViewModel.getBook())
                .addOnCompleteListener( task -> {
                    Snackbar.make( mCoordinatorLayout, "Book added!", Snackbar.LENGTH_SHORT ).show();
                })
                .addOnFailureListener( e -> {
                    Snackbar.make( mCoordinatorLayout, "Book error!", Snackbar.LENGTH_SHORT ).show();
                });
    }
}
