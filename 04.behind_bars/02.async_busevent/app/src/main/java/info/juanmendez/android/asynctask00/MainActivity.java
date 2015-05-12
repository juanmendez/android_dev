package info.juanmendez.android.asynctask00;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    private static final String[] items= { "lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
            "vel", "erat", "placerat", "ante", "porttitor", "sodales",
            "pellentesque", "augue", "purus" };

    private final String list_tag = "list_fragment";
    private final String model_tag = "model_fragment";


    @AfterViews
    void afterViews()
    {
        FragmentManager fm = getFragmentManager();

        if( fm.findFragmentByTag(model_tag) == null )
        {
            Logging.print( "fragment model added");
            MyFragmentModel fragmentModel = MyFragmentModel_.builder().build();
            fm.beginTransaction().add( fragmentModel, model_tag ).commit();
        }

        if( fm.findFragmentByTag(list_tag) == null )
        {
            fm.beginTransaction().replace( R.id.frameLayout, MyListFragment_.builder().build(), list_tag).commit();
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
