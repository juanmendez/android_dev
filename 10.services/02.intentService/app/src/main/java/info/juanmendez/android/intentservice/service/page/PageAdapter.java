package info.juanmendez.android.intentservice.service.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.model.Page;

/**
 * Created by Juan on 8/8/2015.
 */
public class PageAdapter extends FragmentPagerAdapter {

    @Inject
    ArrayList<Fragment> fragments;

    @Inject
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
