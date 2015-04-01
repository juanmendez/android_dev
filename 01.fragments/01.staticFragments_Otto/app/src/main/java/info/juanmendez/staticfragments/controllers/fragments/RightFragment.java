package info.juanmendez.staticfragments.controllers.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.TheApp;
import info.juanmendez.staticfragments.models.LeftChangedEvent;
import info.juanmendez.staticfragments.models.RightChangedEvent;

/**
 * Created by Juan on 3/28/2015.
 */
public class RightFragment extends SideFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        consoleLog("onCreateView");

        View theView = inflater.inflate( R.layout.right_fragment, null );
        editText = (EditText) theView.findViewById(R.id.editText);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    try
                    {
                        //parent.tellLeftFragment(Integer.valueOf(editText.getText().toString()));
                        TheApp.getOtto().post(produceValueEvent());
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

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {

        consoleLog("onCreateView");
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        consoleLog("onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        consoleLog("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {

        super.onResume();
        consoleLog("onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        consoleLog("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {

        super.onPause();
        consoleLog("onPause");
    }

    @Override
    public void onStop() {
        consoleLog("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        consoleLog("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        consoleLog("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        consoleLog("onDetach");
        super.onDetach();
    }

    @Produce
    public RightChangedEvent produceValueEvent()
    {
        if( editText.getText().toString().equals( "" )  )
            return new RightChangedEvent( 0, this );

        return new RightChangedEvent( Integer.valueOf( editText.getText().toString()), this );
    }

    @Subscribe
    public void onValueChanged( LeftChangedEvent event )
    {
        receiveValue( event.getValue() );
    }
}
