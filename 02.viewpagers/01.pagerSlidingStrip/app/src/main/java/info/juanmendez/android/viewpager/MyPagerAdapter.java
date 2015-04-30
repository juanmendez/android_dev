package info.juanmendez.android.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Juan on 4/29/2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter
{
    @Inject
    List<Fragment> fragmentList;

    @Inject
    public MyPagerAdapter( FragmentManager fragmentManager )
    {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    @Override
    public float getPageWidth (int position) {
        return 0.93f;
    }
}
