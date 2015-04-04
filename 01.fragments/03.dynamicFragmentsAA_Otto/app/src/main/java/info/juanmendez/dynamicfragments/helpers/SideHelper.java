package info.juanmendez.dynamicfragments.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.dynamicfragments.R;
import info.juanmendez.dynamicfragments.models.Otto;
import info.juanmendez.dynamicfragments.models.ValueChangedEvent;

@EBean
public class SideHelper {

    @Bean
    Otto otto;

    @RootContext
    Activity parentActivity;

    @ViewById(R.id.editText)
    EditText editText;

    private String fragmentName;

    public SideHelper()
    {
        Otto.consoleLog("Helper: sidehelper init");
    }

    @AfterViews
    void afterViews()
    {
        ValueChangedEvent event = otto.getEvent();

        if( event != null )
        {
            receiveValue( event.getValue() );
        }
    }

    public void setFragmentName(String fragmentName)
    {
        this.fragmentName = fragmentName;
        Otto.consoleLog( "Helper has " + fragmentName );
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
