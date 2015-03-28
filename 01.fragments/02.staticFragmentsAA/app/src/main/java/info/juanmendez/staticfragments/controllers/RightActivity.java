package info.juanmendez.staticfragments.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.controllers.fragments.RightFragment;
import info.juanmendez.staticfragments.controllers.fragments.SideFragment;
import info.juanmendez.staticfragments.helpers.ParentActivity;

/**
 * Created by Juan on 3/28/2015.
 */
@EActivity(R.layout.activity_right)
public class RightActivity extends ActionBarActivity implements ParentActivity {

    @FragmentById( R.id.rightFragment )
    RightFragment right;

    @AfterViews
    void afterViews()
    {
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

        Intent intent = new Intent( this, MainActivity_.class );
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
            Intent intent = new Intent( this, MainActivity_.class );
            intent.putExtra( "value", this.getIntent().getExtras().getInt("value", 0 ) );
            startActivity( intent );
        }
    }

}
