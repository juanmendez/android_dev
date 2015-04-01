package info.juanmendez.dynamicFragments.controllers;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import info.juanmendez.dynamicFragments.R;
import info.juanmendez.dynamicFragments.controllers.fragments.RightFragment;
import info.juanmendez.dynamicFragments.helpers.ParentActivity;

/**
 * Created by Juan on 3/28/2015.
 */
public class RightActivity extends ActionBarActivity implements ParentActivity {
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        consoleLog( "onResume");
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

    @Override
    public void tellRightFragment(int value) {
        try {
            throw new IllegalAccessException( "RightActivity has no LeftFragment to invoke this method");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tellLeftFragment(int value) {

        Intent intent = new Intent( this, MainActivity.class );
        intent.putExtra( "value", value );
        startActivity( intent );
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
