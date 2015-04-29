package info.juanmendez.dynamicfragments.helpers;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import info.juanmendez.dynamicfragments.R;
import info.juanmendez.dynamicfragments.controllers.MainActivity;
import info.juanmendez.dynamicfragments.models.BusEvent;
import info.juanmendez.dynamicfragments.models.ValueChangedEvent;

@EBean
public class SideHelper {

    /**
     * I tried to use SideHelper through Dagger and instantiating it as
     * ActivityModule.sideHelperProvider but what I noticed is other Dagger injections
     * are not created when this class is instantiated. The only injection available is
     * parentActivity which is from AndroidAnnotations
     */
    @Inject
    BusEvent busEvent;

    @Inject
    InputMethodManager inputMethodManager;

    @RootContext
    MainActivity parentActivity;

    @ViewById(R.id.editText)
    EditText editText;

    private String fragmentName;

    @Inject
    public SideHelper(){

    }

    @AfterViews
    void afterViews()
    {
        ValueChangedEvent event = busEvent.produceValueEvent();

        if( event != null )
        {
            receiveValue( event.getValue() );
        }
    }

    public void setFragmentName(String fragmentName)
    {
        this.fragmentName = fragmentName;
    }

    public void hideKeyboard()
    {
        if( editText != null && parentActivity != null )
        {
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
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
