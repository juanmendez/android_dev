package info.juanmendez.android.configfragment;


import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import dagger.ObjectGraph;

@EActivity( R.layout.activity_main )
public class MainActivity extends ActionBarActivity {

    private ObjectGraph graph;

    @Inject
    FragmentManager fm;

    @Inject
    MyFragment myFragment;

    private final String SIMPLE_FRAGMENT_TAG = "myfragmenttag";

    @Override
    public void  onCreate( Bundle bundle ){
        super.onCreate( bundle );

        graph = ObjectGraph.create(new ActivityModule(this));
        inject( this );

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
        {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor( getResources().getColor(R.color.primary_dark));
        }
    }

    @AfterViews
    void afterViews()
    {

        if( fm.findFragmentByTag( SIMPLE_FRAGMENT_TAG ) == null )
        {
            fm.beginTransaction().replace(R.id.frameLayout, myFragment, SIMPLE_FRAGMENT_TAG).commit();
        }
    }

    public void inject(Object object)
    {
        graph.inject(object);
    }
}
