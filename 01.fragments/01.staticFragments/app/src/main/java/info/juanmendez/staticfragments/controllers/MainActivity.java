package info.juanmendez.staticfragments.controllers;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.controllers.fragments.SideFragment;
import info.juanmendez.staticfragments.helpers.ParentActivity;


public class MainActivity extends ActionBarActivity implements ParentActivity {

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
        Log.i(tag, "MainActivity: " + content);
    }

    @Override
    public void tellRightFragment(int value) {

        if( right != null && right.isVisible() )
        {
            right.receiveValue( value );
        }
        else
        {
            Intent intent = new Intent( this, RightActivity.class );
            intent.putExtra( "value", value );
            startActivity( intent );
        }
    }

    @Override
    public void tellLeftFragment(int value) {

        if( getFragmentManager().findFragmentById(R.id.rightFragment) != null )
        {
            left.receiveValue( value );
        }
    }
}
