package info.juanmendez.dynamicFragments.controllers.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import info.juanmendez.dynamicFragments.TheApp;

/**
 * Created by Juan on 3/28/2015.
 */
public class SideFragment extends Fragment {

    protected EditText editText;

    public void receiveValue( int value )
    {
        if( value > 0 )
        {
            editText.setText( Integer.toString(value) );
        }
    }

    protected void initialValue()
    {
        try
        {
            receiveValue( getArguments().getInt( "value", 0 ) );
        }
        catch( NullPointerException e )
        {
            e.printStackTrace();
        }
    }

    protected void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow( editText.getWindowToken(), 0 );
    }

    @Override public void onResume() {
        super.onResume();

        // Register ourselves so that we can provide the initial value.
        getApp().register(this);
    }


    @Override public void onPause() {
        super.onPause();

        // Always unregister when an object no longer should be on the bus.
        getApp().unregister(this);
    }


    protected TheApp getApp()
    {
        return (TheApp) this.getActivity().getApplication();
    }
}