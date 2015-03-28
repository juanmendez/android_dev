package info.juanmendez.staticfragments.controllers.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import info.juanmendez.staticfragments.controllers.MainActivity;
import info.juanmendez.staticfragments.helpers.TestActivity;
import info.juanmendez.staticfragments.helpers.ParentActivity;


/**
 * Created by Juan on 3/28/2015.
 */
public class SideFragment extends Fragment {

    protected ParentActivity parent;
    protected EditText editText;
    protected String fragmentName = "thisFragment";
    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    public String getFragmentName() {
        return fragmentName;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parent = TestActivity.contractPattern(activity);
    }

    public void receiveValue( int value )
    {
        if( value > 0 )
        {
            editText.setText( Integer.toString(value) );
        }
    }

    protected void initialValue()
    {
        /**
         * usually a dynamic fragment can get extras, but in the case of a static one
         * is better to get them directly from the activity.
         * @see http://stackoverflow.com/questions/11387740/where-how-to-getintent-getextras-in-an-android-fragment
         */

        try{
            int value = getActivity().getIntent().getExtras().getInt("value", 0 );
            receiveValue( value );
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

    protected void consoleLog( String content )
    {
        Log.i(MainActivity.tag, fragmentName + " :" + content);
    }
}
