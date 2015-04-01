package info.juanmendez.staticfragments.controllers.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.models.ValueChangedEvent;

public class LeftFragment extends SideFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate( R.layout.left_fragment, null );
        editText = (EditText) theView.findViewById(R.id.editText);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    try
                    {
                        getApp().requestValueChanged(new ValueChangedEvent( Integer.valueOf( editText.getText().toString()), LeftFragment.this ));
                        hideKeyboard();
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


        initialValue();
        return theView;
    }


    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        if( event.getTarget() != this )
            receiveValue( event.getValue() );
    }
}
