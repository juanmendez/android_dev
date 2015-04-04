package info.juanmendez.dynamicfragments.controllers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.dynamicfragments.R;
import info.juanmendez.dynamicfragments.controllers.fragments.LeftFragment_;
import info.juanmendez.dynamicfragments.controllers.fragments.RightFragment_;
import info.juanmendez.dynamicfragments.models.Otto;
import info.juanmendez.dynamicfragments.models.ValueChangedEvent;

@EActivity( R.layout.activity_main )
public class MainActivity extends ActionBarActivity
{
    @Bean
    Otto otto;

    @ViewById( R.id.rightContainer )
    FrameLayout rightLayout;

    @ViewById( R.id.leftContainer )
    FrameLayout leftLayout;

    Fragment right;
    Fragment left;

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

    @AfterViews
    void afterViews()
    {
        FragmentTransaction fts = getFragmentManager().beginTransaction();

        right = new RightFragment_();
        left = new LeftFragment_();

        fts.replace(R.id.leftContainer, left);
        fts.addToBackStack("left");

        if( rightLayout != null )
        {
            fts.replace(R.id.rightContainer, right);
            fts.addToBackStack( "right");
        }

        fts.commit();
    }

    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        if( rightLayout == null )
        {
            FragmentTransaction fts = getFragmentManager().beginTransaction();
            Fragment f = left;
            String tag = "left";

            if( !right.isVisible() )
            {
                right = new RightFragment_();
                f = right;
                tag = "right";
            }
            else
            {
                left = new LeftFragment_();
                f = left;
                tag = "left";
            }

            fts.replace(R.id.leftContainer, f);
            fts.addToBackStack( tag );
            fts.commit();
        }
    }
}