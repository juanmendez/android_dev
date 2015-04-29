package info.juanmendez.android.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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


    @Provides
    @Singleton
    public FragmentManager fragmentManagerProvider()
    {
        return mainActivity.getSupportFragmentManager();
    }

    @Provides
    @Singleton
    public List<Fragment> fragmentListProvider()
    {
        List<Fragment> list = new ArrayList<Fragment>();

        list.add( FirstFragment.newInstance( 0, "Page # 1", R.color.translucent_red) );
        list.add( FirstFragment.newInstance( 1, "Page # 2", R.color.orange ) );
        list.add( FirstFragment.newInstance( 2, "Page # 3", R.color.opaque_red ) );

        return list;
    }
}
