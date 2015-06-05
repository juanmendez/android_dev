package com.commonsware.android.frw.filesdemo.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.commonsware.android.frw.filesdemo.PageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 4/29/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter
{
    List<PageFragment> fragmentList;

    public PagerAdapter(FragmentManager fragmentManager, List<PageFragment> _fragmentList )
    {
        super(fragmentManager);
        fragmentList = _fragmentList;
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
        return fragmentList.get(position).getPage().getTitle();
    }


    @Override
    public float getPageWidth (int position) {
        return 0.93f;
    }
}
