package info.juanmendez.android.preferencefragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EFragment;


/**
 * Created by Juan Mendez on 10/19/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 * Dialog wraps FragmentPref
 */
@EFragment
public class DialogPreference extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate( R.layout.layout_dialogpreference, container, false );
        return v;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }
}