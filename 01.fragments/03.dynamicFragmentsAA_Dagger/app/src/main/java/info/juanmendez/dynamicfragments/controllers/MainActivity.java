package info.juanmendez.dynamicfragments.controllers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import dagger.ObjectGraph;
import info.juanmendez.dynamicfragments.R;
import info.juanmendez.dynamicfragments.controllers.fragments.LeftFragment_;
import info.juanmendez.dynamicfragments.controllers.fragments.RightFragment_;
import info.juanmendez.dynamicfragments.models.BusEvent;
import info.juanmendez.dynamicfragments.models.ValueChangedEvent;
import info.juanmendez.dynamicfragments.modules.ActivityModule;

@EActivity( R.layout.activity_main )
public class MainActivity extends ActionBarActivity
{

    @Inject
    BusEvent busEvent;


    @ViewById( R.id.rightContainer )
    FrameLayout rightLayout;

    @ViewById( R.id.leftContainer )
    FrameLayout leftLayout;

    Fragment right;
    Fragment left;

    private ObjectGraph activityGraph;

    @Override
    protected void onPause() {
        super.onPause();
        busEvent.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        busEvent.register(this);
    }

    @AfterViews
    void afterViews()
    {
        TheApplication application = (TheApplication) getApplication();

        activityGraph = application.getApplicationGraph().plus( new ActivityModule(this) );
        activityGraph.inject(this);

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

    /** Inject the supplied {@code object} using the activity-specific graph. */
    public void inject(Object object) {
        activityGraph.inject(object);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        activityGraph = null;
        super.onDestroy();
    }
}