package info.juanmendez.dynamicfragments.controllers.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import info.juanmendez.dynamicfragments.R;
import info.juanmendez.dynamicfragments.controllers.MainActivity;
import info.juanmendez.dynamicfragments.helpers.FragmentHelper;
import info.juanmendez.dynamicfragments.helpers.SideHelper;
import info.juanmendez.dynamicfragments.models.BusEvent;
import info.juanmendez.dynamicfragments.models.ValueChangedEvent;

/**
 * Created by Juan on 3/28/2015.
 */
@EFragment( R.layout.left_fragment )
public class LeftFragment extends TheFragment
{
    @Inject
    FragmentHelper fragmentHelper;

    @ViewById (R.id.editText)
    EditText editText;

    @Inject
    BusEvent busEvent;

    /**
     * in order to listen to changes,, make sure it is once the Activity is created.
     * @AfterViews is called when fragment has been created and is first available.
     * That still requires to wait for Activity. As I was waiting for AfterViews, I noticed
     * the editText reference in FragmentHelper being lost.
     * @param savedInstanceState
     */
    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).inject( this );

        if( editText != null )
        {
            fragmentHelper.setEditText( editText );
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        try
                        {
                            busEvent.requestValueChanged(new ValueChangedEvent(Integer.valueOf(editText.getText().toString()), LeftFragment.this));
                            fragmentHelper.hideKeyboard();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override public void onResume() {
        super.onResume();

        // Register ourselves so that we can provide the initial value.
        busEvent.register(this);
    }


    @Override public void onPause() {
        super.onPause();

        // Always unregister when an object no longer should be on the bus.
        busEvent.unregister(this);
    }

    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        if( event.getTarget() != this )
            fragmentHelper.receiveValue( event.getValue() );
    }
}
