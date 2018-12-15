package info.juanmendez.introfirebase.authenticate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import info.juanmendez.introfirebase.R;
import info.juanmendez.introfirebase.model.Book;

/**
 * Created by Juan on 3/12/2016.
 */
public class DeleteBookDialog extends RxDialogFragment<Boolean> {

    public static DeleteBookDialog newInstance(Book book) {
        DeleteBookDialog frag = new DeleteBookDialog();
        Bundle args = new Bundle();
        args.putString( "title", "Do you want to delete " +  book.getTitle() + "?" );
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.messenger_bubble_small_white)
                .setTitle(title)
                .setPositiveButton( "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                onNext( true );
                            }
                        }
                )
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                onNext( false );
                            }
                        }
                )
                .create();
    }
}