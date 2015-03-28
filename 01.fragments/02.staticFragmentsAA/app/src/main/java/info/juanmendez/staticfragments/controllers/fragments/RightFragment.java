package info.juanmendez.staticfragments.controllers.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.staticfragments.R;

/**
 * Created by Juan on 3/28/2015.
 */
@EFragment( R.layout.right_fragment )
public class RightFragment extends SideFragment
{
    @ViewById (R.id.editText)
    EditText editText;

    @AfterViews
    void afterViews()
    {
        if( editText != null )
        {
            setTextRef(editText);

            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_SEND) {

                        try
                        {
                            parent.tellLeftFragment(Integer.valueOf(editText.getText().toString()));
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
        }

        initialValue();
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
        consoleLog("onResume");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        consoleLog("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        consoleLog("onPause");
        super.onPause();
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
}
