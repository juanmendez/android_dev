package info.juanmendez.staticfragments.controllers;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import info.juanmendez.staticfragments.R;
import info.juanmendez.staticfragments.controllers.fragments.RightFragment;
import info.juanmendez.staticfragments.models.Otto;
import info.juanmendez.staticfragments.models.ValueChangedEvent;

/**
 * Created by Juan on 3/28/2015.
 */
@EActivity(R.layout.activity_right)
public class RightActivity extends ActionBarActivity {

    @Bean
    Otto otto;

    @FragmentById( R.id.rightFragment )
    RightFragment right;

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

    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        if( event.getTarget() == right )
        {
            Intent intent = new Intent( this, MainActivity_.class );
            intent.putExtra( "value", event.getValue() );
            startActivity( intent );
        }
    }

}
