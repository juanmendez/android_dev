package info.juanmendez.dynamicFragments.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import info.juanmendez.dynamicFragments.R;
import info.juanmendez.dynamicFragments.TheApp;
import info.juanmendez.dynamicFragments.controllers.fragments.LeftFragment;
import info.juanmendez.dynamicFragments.controllers.fragments.RightFragment;
import info.juanmendez.dynamicFragments.controllers.fragments.SideFragment;
import info.juanmendez.dynamicFragments.models.ValueChangedEvent;

/**
 * upon activity initialization we can check
 * for saved instance state for last valueChanged 01.
 * otherwise, the app can hold such value 02
 */
public class MainActivity extends Activity
{
    SideFragment right;
    SideFragment left;
    ValueChangedEvent valueChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fts = getFragmentManager().beginTransaction();

        right = new RightFragment();
        left = new LeftFragment();

        //01
        /**
        if( savedInstanceState != null )
        {
            int value = savedInstanceState.getInt( "value",0);

            if( value != 0 )
            {
                Bundle args = new Bundle();
                args.putInt("value", value );
                right.setArguments( args );
                left.setArguments( args );
            }
        }**/

        fts.replace(R.id.leftContainer, left);
        fts.addToBackStack("left");

        if( findViewById(R.id.rightContainer ) != null  )
        {
            fts.replace(R.id.rightContainer, right);
            fts.addToBackStack( "right");
        }

        if( valueChanged != null )
        {
            TheApp.consoleLog( "mainActivity: " + valueChanged.toString() );
        }

        fts.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ((TheApp)getApplication()).unregister(this);
    }

    //01
    /**
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if( valueChanged != null )
        {
            outState.putInt( "value", valueChanged.getValue() );
        }

        super.onSaveInstanceState(outState);
    }**/

    @Override
    protected void onResume() {
        super.onResume();

        ((TheApp)getApplication()).register(this);
    }
    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        valueChanged = event;

        if( findViewById(R.id.rightContainer ) == null  )
        {
            FragmentTransaction fts = getFragmentManager().beginTransaction();
            Fragment f = left;
            String tag = "left";

            if( !right.isVisible() )
            {
                right = new RightFragment();
                f = right;
                tag = "right";
            }
            else
            {
                left = new LeftFragment();
                f = left;
                tag = "left";
            }

            /*01
            Bundle args = new Bundle();
            args.putInt("value", event.getValue() );
            f.setArguments( args );
            */

            fts.replace(R.id.leftContainer, f );
            fts.addToBackStack( tag );
            fts.commit();
        }
    }
}