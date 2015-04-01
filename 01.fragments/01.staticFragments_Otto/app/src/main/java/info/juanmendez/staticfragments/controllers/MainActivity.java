package info.juanmendez.staticfragments.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.squareup.otto.Subscribe;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.TheApp;
import info.juanmendez.staticfragments.controllers.fragments.SideFragment;
import info.juanmendez.staticfragments.models.ValueChangedEvent;


public class MainActivity extends Activity
{

    public static final String tag = "staticFragment";
    SideFragment right;
    SideFragment left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        right = (SideFragment) getFragmentManager().findFragmentById(R.id.rightFragment);

        if( right != null )
            right.setFragmentName( "Right-Landscape");

        left = (SideFragment) getFragmentManager().findFragmentById(R.id.leftFragment );

        if( left != null )
        {
            if( right!= null )
                left.setFragmentName( "Left-Landscape" );
            else
                left.setFragmentName( "Left-Portrait" );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        ((TheApp)getApplication()).unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ((TheApp)getApplication()).register(this);
    }
    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        if( event.getTarget() == left && ( right == null || !right.isVisible() ) )
        {
            Intent intent = new Intent( this, RightActivity.class );
            intent.putExtra( "value", event.getValue() );
            startActivity( intent );
        }
    }
}
