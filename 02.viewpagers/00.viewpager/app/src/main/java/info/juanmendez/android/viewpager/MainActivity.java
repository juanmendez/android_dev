package info.juanmendez.android.viewpager;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity( R.layout.activity_main )
public class MainActivity extends ActionBarActivity {

    @ViewById
    FrameLayout firstFrameLayout;

    @ViewById
    FrameLayout secondFrameLayout;

    @AfterViews
    void afterViews()
    {
        FragmentTransaction fts = getFragmentManager().beginTransaction();
        FirstFragment f1 = FirstFragment.newInstance( 1, "title1", R.color.opaque_red );
        FirstFragment f2 = FirstFragment.newInstance( 2, "title2", R.color.translucent_red );

        fts.replace( R.id.firstFrameLayout, f1 );
        fts.addToBackStack( "first" );
        fts.replace( R.id.secondFrameLayout, f2 );
        fts.addToBackStack( "second" );

        fts.commit();
    }
}
