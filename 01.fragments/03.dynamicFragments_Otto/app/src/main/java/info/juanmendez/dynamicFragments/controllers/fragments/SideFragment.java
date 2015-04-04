package info.juanmendez.dynamicFragments.controllers.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import info.juanmendez.dynamicFragments.TheApp;
import info.juanmendez.dynamicFragments.models.ValueChangedEvent;

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

    /**
     * upon initialization of the fragment, we can
     * check for fragment arguments 01, but on second thought
     * we can based our arguments on the last event produced
     * at the app level 02.
     */
    protected void initialValue()
    {
        try
        {
            //01
            //receiveValue( getArguments().getInt( "value", 0 ) );
            //\01

            //02
            ValueChangedEvent event = getApp().produceValueEvent();

            if( event != null )
            {
                receiveValue( event.getValue() );
            }
            //\02
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