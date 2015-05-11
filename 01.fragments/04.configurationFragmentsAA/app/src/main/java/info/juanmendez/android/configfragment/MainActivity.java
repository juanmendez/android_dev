package info.juanmendez.android.configfragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews()
    {
        graph = ObjectGraph.create(new ActivityModule(this));
        inject( this );

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
