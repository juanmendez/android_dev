package info.juanmendez.introfirebase.authenticate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.model.Book;

/**
 * Created by Juan on 3/12/2016.
 */
public class AddBookDialog extends RxDialogFragment<Book> {

    public static AddBookDialog newInstance() {
        AddBookDialog frag = new AddBookDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");

        View contentView = getActivity().getLayoutInflater()
                .inflate(R.layout.book_dialog_content, null);

        TextView titleText = (TextView) contentView.findViewById(R.id.titleText);
        TextView authorText = (TextView) contentView.findViewById(R.id.authorText);
        TextView urlText = (TextView) contentView.findViewById(R.id.urlText);

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.messenger_bubble_small_white)
                .setTitle(title)
                .setView( contentView )
                .setPositiveButton( "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Book book = new Book();
                                book.setShopUrl( urlText.getText().toString() );
                                book.setAuthor(authorText.getText().toString());
                                book.setTitle(titleText.getText().toString());

                                subject.onNext( book );
                            }
                        }
                )
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                subject.onError( new Throwable( "no book selected"));
                            }
                        }
                )
                .create();
    }
}
