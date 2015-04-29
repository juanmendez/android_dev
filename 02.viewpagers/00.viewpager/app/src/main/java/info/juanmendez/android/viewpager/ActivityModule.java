package info.juanmendez.android.viewpager;

import dagger.Module;

/**
 * Created by Juan on 4/29/2015.
 */
@Module(
        injects = {
                MainActivity_.class,
                FirstFragment_.class
        },
        library = true
)
public class ActivityModule
{
    private final MainActivity mainActivity;

    public ActivityModule( MainActivity activity )
    {
        mainActivity = activity;
    }


}
