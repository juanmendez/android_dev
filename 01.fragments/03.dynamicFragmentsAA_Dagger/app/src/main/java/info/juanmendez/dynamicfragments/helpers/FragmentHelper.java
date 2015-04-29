package info.juanmendez.dynamicfragments.helpers;

import android.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import javax.inject.Inject;

import info.juanmendez.dynamicfragments.controllers.MainActivity;
import info.juanmendez.dynamicfragments.models.BusEvent;
import info.juanmendez.dynamicfragments.models.LoggingManager;
import info.juanmendez.dynamicfragments.models.ValueChangedEvent;

/**
 * Created by Juan on 4/22/2015.
 */
public class FragmentHelper {

    @Inject
    BusEvent busEvent;

    @Inject
    InputMethodManager inputMethodManager;

    @Inject
    MainActivity mainActivity;

    public EditText editText;

    public void setEditText(EditText _editText)
    {
        editText = _editText;

        ValueChangedEvent event = busEvent.produceValueEvent();

        if( event != null )
        {
            receiveValue( event.getValue() );
        }
    }

    public void hideKeyboard()
    {
        if( editText != null )
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void receiveValue( int value )
    {
        if( value > 0 && editText != null )
        {
            editText.setText(Integer.toString(value));
        }
    }

    @Inject
    public FragmentHelper()
    {

    }
}
