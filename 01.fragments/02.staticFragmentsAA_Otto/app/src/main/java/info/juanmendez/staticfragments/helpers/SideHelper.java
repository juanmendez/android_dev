package info.juanmendez.staticfragments.helpers;
import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.controllers.MainActivity;

@EBean
public class SideHelper {

    @RootContext
    Activity parentActivity;

    @ViewById(R.id.editText)
    EditText editText;

    private String fragmentName;

    public SideHelper()
    {
        MainActivity.consoleLog( "Helper: sidehelper init" );
    }

    @AfterViews
    void afterViews()
    {
        /**
         * usually a dynamic fragment can get extras, but in the case of a static one
         * is better to get them directly from the activity.
         * @see http://stackoverflow.com/questions/11387740/where-how-to-getintent-getextras-in-an-android-fragment
         */

        try{
            int value = parentActivity.getIntent().getExtras().getInt("value", 0 );
            receiveValue( value );
        }
        catch( NullPointerException e )
        {
            e.printStackTrace();
        }
    }

    public void setFragmentName(String fragmentName)
    {
        this.fragmentName = fragmentName;

        MainActivity.consoleLog( "Helper has " + fragmentName );
    }

    public void hideKeyboard()
    {
        if( editText != null && parentActivity != null )
        {
            InputMethodManager imm = (InputMethodManager)parentActivity.getSystemService(Context.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public void receiveValue( int value )
    {
        if( value > 0 )
        {
            editText.setText(Integer.toString(value));
        }
    }
}
