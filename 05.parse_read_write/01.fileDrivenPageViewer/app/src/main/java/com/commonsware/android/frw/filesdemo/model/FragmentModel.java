package com.commonsware.android.frw.filesdemo.model;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.commonsware.android.frw.filesdemo.PageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 6/4/2015.
 */
public class FragmentModel extends Fragment
{
    List<PageFragment> fragmentList = new ArrayList<PageFragment>();

    public Boolean getLoaded() {
        return _loaded;
    }

    public void setLoaded(Boolean _loaded) {
        this._loaded = _loaded;
    }

    Boolean _loaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public List<PageFragment> getFragmentList() {
        return fragmentList;
    }
}
