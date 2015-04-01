package info.juanmendez.staticfragments.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.squareup.otto.Subscribe;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.TheApp;
import info.juanmendez.staticfragments.controllers.fragments.RightFragment;
import info.juanmendez.staticfragments.models.ValueChangedEvent;

/**
 * Created by Juan on 3/28/2015.
 */
public class RightActivity extends Activity
{
    RightFragment right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        consoleLog( "onCreate");
        setContentView( R.layout.activity_right );

        right = (RightFragment) getFragmentManager().findFragmentById(R.id.rightFragment);

        if( right != null )
            right.setFragmentName( "Right-Portrait");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        consoleLog( "onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        consoleLog( "onRestart");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        consoleLog( "onSaveInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();

        consoleLog( "onPause");
        TheApp.getOtto().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        consoleLog( "onResume");
        TheApp.getOtto().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        consoleLog( "onStart");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        consoleLog( "onDestroy" );
    }

    private void consoleLog( String content )
    {
        Log.i(MainActivity.tag, "RightActivity: " + content);
    }

    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {

        if( event.getTarget() == right )
        {
            Intent intent = new Intent( this, MainActivity.class );
            intent.putExtra( "value", event.getValue() );
            startActivity( intent );
        }
    }

    /**
     * lets help the user see both fragments..
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged( Configuration newConfig )
    {
        super.onConfigurationChanged( newConfig );

        if( newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE )
        {
            Intent intent = new Intent( this, MainActivity.class );
            intent.putExtra( "value", this.getIntent().getExtras().getInt("value", 0 ) );
            startActivity( intent );
        }
    }

}
