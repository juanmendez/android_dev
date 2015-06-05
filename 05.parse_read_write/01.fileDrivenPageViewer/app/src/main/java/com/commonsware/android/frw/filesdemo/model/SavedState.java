package com.commonsware.android.frw.filesdemo.model;

import android.support.v4.app.FragmentManager;

import com.commonsware.android.frw.filesdemo.PageFragment;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 6/4/2015.
 */
public class SavedState {

    List<PageFragment> fragmentList = new ArrayList<PageFragment>();
    PagerAdapter pagerAdapter;

    public List<PageFragment> getFragmentList() {
        return fragmentList;
    }

    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(FragmentManager fm) {
        this.pagerAdapter = new PagerAdapter(fm, fragmentList );
    }
}
