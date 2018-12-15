package info.juanmendez.realminit.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import info.juanmendez.realminit.R;
import info.juanmendez.realminit.RealmApplication;
import info.juanmendez.realminit.models.Band;
import info.juanmendez.realminit.services.BandService;

/**
 * Created by musta on 8/27/2016.
 */

public class BandFormDialog extends DialogFragment{

    @Inject
    BandService bandService;

    public BandFormDialog() {
        RealmApplication.inject( this );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View dialogLayout = inflater.inflate(R.layout.band_form, null);
        builder.setView( dialogLayout );
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                EditText editText = (EditText) dialogLayout.findViewById( R.id.bandTitleInput);
                Band band = new Band();
                band.setName( editText.getText().toString() );

                if( !band.getName().isEmpty() )
                    bandService.add( band );

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        return builder.create();
    }
}