package info.juanmendez.dynamicfragments.controllers.fragments;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.dynamicfragments.R;
import info.juanmendez.dynamicfragments.helpers.SideHelper;
import info.juanmendez.dynamicfragments.models.Otto;
import info.juanmendez.dynamicfragments.models.ValueChangedEvent;

/**
 * Created by Juan on 3/28/2015.
 */
@EFragment( R.layout.right_fragment )
public class RightFragment extends Fragment
{
    @Bean
    Otto otto;

    @Bean
    SideHelper helper;

    @ViewById (R.id.editText)
    EditText editText;

    @AfterViews
    void afterViews()
    {
        if( editText != null )
        {
            helper.setFragmentName( "rightFragment" );
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_SEND) {

                        try
                        {
                            helper.hideKeyboard();
                            otto.requestValueChanged(new ValueChangedEvent(Integer.valueOf(editText.getText().toString()), RightFragment.this));
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
    }

    @Override public void onResume() {
        super.onResume();

        // Register ourselves so that we can provide the initial value.
        otto.register(this);
    }


    @Override public void onPause() {
        super.onPause();

        // Always unregister when an object no longer should be on the bus.
        otto.unregister(this);
    }

    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        if( event.getTarget() != this )
        {
            helper.receiveValue(event.getValue());
        }
    }
}
