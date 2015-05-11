package info.juanmendez.android.configfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

/**
 * Created by Juan on 4/30/2015.
 */

@EFragment(R.layout.my_fragment_layout)
public class MyFragment extends Fragment {

    @ViewById
    EditText myEditText;

    @Inject
    InputMethodManager inputMethodManager;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setRetainInstance(true);

    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).inject(this);


        myEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_NEXT ) {
                    try
                    {
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

    @AfterViews
    void afterViews()
    {
    }

    public void hideKeyboard()
    {
        inputMethodManager.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }
}
