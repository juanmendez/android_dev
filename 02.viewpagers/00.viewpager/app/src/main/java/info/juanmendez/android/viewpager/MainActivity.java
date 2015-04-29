package info.juanmendez.android.viewpager;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import dagger.ObjectGraph;

@EActivity( R.layout.activity_main )
public class MainActivity extends ActionBarActivity {

    @ViewById
    FrameLayout firstFrameLayout;

    @ViewById
    FrameLayout secondFrameLayout;

    private ObjectGraph graph;

    @AfterViews
    void afterViews()
    {
        graph = ObjectGraph.create( new ActivityModule( this ));
        inject( this );
        FragmentTransaction fts = getFragmentManager().beginTransaction();
        FirstFragment f1 = FirstFragment.newInstance( 1, "title1", R.color.opaque_red );
        FirstFragment f2 = FirstFragment.newInstance( 2, "title2", R.color.translucent_red );

        fts.replace( R.id.firstFrameLayout, f1 );
        fts.addToBackStack( "first" );
        fts.replace( R.id.secondFrameLayout, f2 );
        fts.addToBackStack( "second" );
        fts.commit();
    }

    public void inject(Object object)
    {
        graph.inject(object);
    }
}
