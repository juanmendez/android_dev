package info.juanmendez.android.viewpager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import dagger.ObjectGraph;

@EActivity( R.layout.activity_main )
public class MainActivity extends ActionBarActivity {

    private ObjectGraph graph;

    @Inject
    MyPagerAdapter myPagerAdapter;

    @ViewById
    ViewPager vpPager;

    @ViewById
    PagerSlidingTabStrip tabStrip;

    @AfterViews
    void afterViews()
    {
        graph = ObjectGraph.create( new ActivityModule( this ));
        inject( this );

        vpPager.setAdapter( myPagerAdapter );
        tabStrip.setViewPager( vpPager );
    }

    public void inject(Object object)
    {
        graph.inject(object);
    }
}
