package com.commonsware.android.frw.filesdemo.service;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.inputmethod.InputMethodManager;

import com.commonsware.android.frw.filesdemo.MainActivity;
import com.commonsware.android.frw.filesdemo.MainActivity_;
import com.commonsware.android.frw.filesdemo.PageFragment;
import com.commonsware.android.frw.filesdemo.PageFragment_;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Juan on 5/30/2015.
 */
@Module(
        injects = {
                MainActivity_.class,
                PageFragment_.class
        },
        addsTo = AppModule.class,
        library = true
)
public class ActivityModule {

    private final MainActivity _activity;

    public ActivityModule( MainActivity activity )
    {
        _activity = activity;
    }

    @Provides
    @Singleton
    public MainActivity mainActivityProvider()
    {
        return _activity;
    }

    @Provides
    public InputMethodManager inputMethodManagerProvider()
    {
        return (InputMethodManager)_activity.getSystemService(Context.INPUT_METHOD_SERVICE );
    }

    @Provides
    @Singleton
    public FragmentManager fragmentManagerProvider()
    {
        return _activity.getSupportFragmentManager();
    }

    @Provides
    @Singleton
    public List<PageFragment> fragmentListProvider()
    {
        List<PageFragment> list = new ArrayList<PageFragment>();
        return list;
    }
}
