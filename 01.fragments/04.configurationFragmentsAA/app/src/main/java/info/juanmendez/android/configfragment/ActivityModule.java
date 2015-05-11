package info.juanmendez.android.configfragment;

import android.app.FragmentManager;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import org.androidannotations.annotations.EBean;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Juan on 4/29/2015.
 */
@Module(
        injects = {
                MainActivity_.class,
                MyFragment_.class,
                ActivityHolder_.class
        },
        library = true
)
public class ActivityModule
{
    private final MainActivity mainActivity;

    public ActivityModule(MainActivity activity)
    {
        mainActivity = activity;
    }


    @Provides
    @Singleton
    public FragmentManager fragmentManagerProvider()
    {
        return mainActivity.getFragmentManager();
    }

    @Provides
    @Singleton
    public MyFragment fragmentProvider()
    {
        Logging.print( "fragment created!" );
        return MyFragment_.builder().build();
    }

    @Provides
    @Singleton
    public InputMethodManager inputMethodManagerProvider()
    {
        return (InputMethodManager)mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE );
    }
}
