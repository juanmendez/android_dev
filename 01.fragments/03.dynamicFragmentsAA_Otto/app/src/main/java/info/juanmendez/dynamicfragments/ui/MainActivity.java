package info.juanmendez.dynamicfragments.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.dynamicfragments.R;
import info.juanmendez.dynamicfragments.ui.fragments.LeftFragment;
import info.juanmendez.dynamicfragments.ui.fragments.LeftFragment_;
import info.juanmendez.dynamicfragments.ui.fragments.RightFragment;
import info.juanmendez.dynamicfragments.ui.fragments.RightFragment_;
import info.juanmendez.dynamicfragments.models.Otto;
import info.juanmendez.dynamicfragments.models.ValueChangedEvent;

@EActivity( R.layout.activity_main )
public class MainActivity extends AppCompatActivity
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
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();


        if( singlePane())
        {
            ValueChangedEvent event = otto.produceValueEvent();

            //something seems not right..
            if( event != null && event.getTarget() instanceof RightFragment ){

                left = new LeftFragment_();
                fts.replace( R.id.leftContainer, left);
                fts.addToBackStack("left");
            }
            else
            {
                right = new RightFragment_();
                fts.replace(R.id.leftContainer, right);
                fts.addToBackStack("right");
            }
        }
        else
        {
            right = new RightFragment_();
            left = new LeftFragment_();

            fts.replace(R.id.leftContainer, left);
            fts.addToBackStack("left");

            fts.replace(R.id.rightContainer, right);
            fts.addToBackStack("right");
        }

        fts.commit();
    }


    private boolean singlePane(){
        return rightLayout == null;
    }

    @Subscribe
    public void onValueChanged( ValueChangedEvent event )
    {
        if( singlePane() )
        {
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            Fragment f;
            String tag;

            if( !right.isVisible() )
            {
                right = new RightFragment_(); //red
                f = right;
                tag = "right";
            }
            else
            {
                left = new LeftFragment_(); //light blue
                f = left;
                tag = "left";
            }

            fts.replace(R.id.leftContainer, f);
            fts.addToBackStack( tag );
            fts.commit();
        }
    }
}