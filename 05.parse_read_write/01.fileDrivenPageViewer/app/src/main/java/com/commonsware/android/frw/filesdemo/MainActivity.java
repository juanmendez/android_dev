package com.commonsware.android.frw.filesdemo;

import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.model.ActivityModule;
import com.commonsware.android.frw.filesdemo.model.MenuItemEvent;
import com.commonsware.android.frw.filesdemo.model.Page;
import com.commonsware.android.frw.filesdemo.model.PagerAdapter;
import com.commonsware.android.frw.filesdemo.service.BuildTask;
import com.commonsware.android.frw.filesdemo.service.BusHandler;
import com.commonsware.android.frw.filesdemo.service.LoadTask;
import com.fasterxml.jackson.jr.ob.JSON;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

@EActivity(R.layout.main)
@OptionsMenu(R.menu.actions)
public class MainActivity extends ActionBarActivity {

    private ObjectGraph graph;

    @Inject
    PagerAdapter pagerAdapter;

    @ViewById
    ViewPager vpPager;

    @ViewById
    PagerSlidingTabStrip tabStrip;

    @Bean
    LoadTask loadTask;

    @Bean
    BuildTask buildTask;

    @Bean
    BusHandler busHandler;

    @Inject
    List<PageFragment> fragmentList;

    @Inject
    FragmentManager fm;

    @Override
    public void onPause()
    {
        super.onPause();
        busHandler.unregister(this);
        graph = null;
    }

    @AfterViews
    void afterViews()
    {

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            StrictMode.setThreadPolicy(buildPolicy());
        }

        busHandler.register(this);

        graph = ((TheApp)getApplication()).getApplicationGraph().plus(new ActivityModule( this ));
        inject(this);

        vpPager.setAdapter(pagerAdapter);
        //tabStrip.setViewPager( vpPager );

        vpPager.setOffscreenPageLimit(3);
        vpPager.setClipToPadding(false);
        vpPager.setPageMargin(12);


        //buildTask.execute();
        loadTask.execute((new File(getFilesDir(), "pages.json")));
    }

    private StrictMode.ThreadPolicy buildPolicy()
    {
        return (new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog()
                .build());
    }

    public void inject(Object object)
    {
        graph.inject(object);
    }

    @Subscribe
    public void actionEventSubscriber( ActionEvent event )
    {
        if( event.getAction() == ActionEvent.ActionType.LOAD )
        {
            /**
             * after loading, lets use the new list  to generate pages for pagerviewer,
             * and of course, through the adapter. Also lets not forget to update the adapter
             * to show the new pages!
             */
            if( event.getFile().getName().equals("pages.json") )
            {
                try {

                    List<Page> list = JSON.std.listOfFrom( Page.class, event.getContent() );

                    for( Page page: list)
                    {
                        fragmentList.add(PageFragment.newInstance(page));
                    }

                    pagerAdapter.notifyDataSetChanged();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * We want fragments withing PagerViewer to know when any selected menuItem
     * has been picked. So BusHandler does that for us, all we need is to assign
     * a new MenuItemEvent. I wanted to use directly the MenuItem instance but there was a precompiled error
     * from Otto telling that the object is not from a concrete class, so I decided to embed it.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        busHandler.requestMenuItem( new MenuItemEvent( item ) );
        return (super.onOptionsItemSelected(item));
    }
}