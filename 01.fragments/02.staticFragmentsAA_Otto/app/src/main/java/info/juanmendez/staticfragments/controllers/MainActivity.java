package info.juanmendez.staticfragments.controllers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.controllers.fragments.LeftFragment;
import info.juanmendez.staticfragments.controllers.fragments.RightFragment;
import info.juanmendez.staticfragments.models.Otto;
import info.juanmendez.staticfragments.models.ValueChangedEvent;

@EActivity( R.layout.activity_main )
public class MainActivity extends ActionBarActivity
{
    @Bean
    Otto otto;

    public static final String tag = "staticFragment";

    @FragmentById( R.id.rightFragment )
    RightFragment right;

    @FragmentById( R.id.leftFragment )
    LeftFragment left;

    @Override
    protected void onPause() {
        super.onPause();
        otto.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        otto.register(this);
    }

    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        if( event.getTarget() == left && ( right == null || !right.isVisible() ) )
        {
            Intent intent = new Intent( this, RightActivity_.class );
            intent.putExtra( "value", event.getValue() );
            startActivity( intent );
        }
    }

    public static void consoleLog( String content )
    {
        Log.e( MainActivity.tag, content );
    }
}