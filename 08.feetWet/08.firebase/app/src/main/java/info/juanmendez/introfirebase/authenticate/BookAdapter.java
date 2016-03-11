package info.juanmendez.introfirebase.authenticate;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.model.Book;

/**
 * Created by Juan on 3/8/2016.
 */
public class BookAdapter extends FirebaseListAdapter<Book> {
    public BookAdapter(Activity activity, Class<Book> modelClass, int modelLayout, Firebase ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, Book book, int position) {
        TextView textBook = (TextView) v.findViewById(R.id.textBookTitle );
        textBook.setText( book.getTitle() );
        //super.populateView(v, book, position);
    }
}
