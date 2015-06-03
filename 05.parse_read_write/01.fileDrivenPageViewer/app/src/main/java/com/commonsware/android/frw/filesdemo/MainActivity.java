package com.commonsware.android.frw.filesdemo;

import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.service.ActivityModule;
import com.commonsware.android.frw.filesdemo.model.MenuItemEvent;
import com.commonsware.android.frw.filesdemo.model.Page;
import com.commonsware.android.frw.filesdemo.model.PagerAdapter;
import com.commonsware.android.frw.filesdemo.service.BusHandler;
import com.commonsware.android.frw.filesdemo.service.LoadTask;
import com.commonsware.android.frw.filesdemo.service.SaveTask;
import com.commonsware.android.frw.filesdemo.utils.Logging;
import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONComposer;
import com.fasterxml.jackson.jr.ob.comp.ArrayComposer;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
    SaveTask saveTask;

    @Bean
    BusHandler busHandler;

    @Inject
    List<PageFragment> fragmentList;

    @Inject
    FragmentManager fm;

    private Boolean loaded = false;

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
        loadTask.execute((new File(getFilesDir(), "all_pages.json")));
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
        if( event.getFile().getName().equals("all_pages.json") )
        {
            if( event.getAction() == ActionEvent.ActionType.LOAD )
            {
                /**
                 * after loading, lets use the new list  to generate pages for pagerviewer,
                 * and of course, through the adapter. Also lets not forget to update the adapter
                 * to show the new pages!
                 */
                loaded = true;
                try {
                    List<Page> list;
                    list = JSON.std.listOfFrom( Page.class, event.getContent() );

                    for( Page page: list)
                    {
                        fragmentList.add(PageFragment.newInstance(page));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally
                {
                    pagerAdapter.notifyDataSetChanged();
                }
            }
            else
            if( event.getAction() == ActionEvent.ActionType.SAVE )
            {
                Logging.print( "save page.json" );
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
        if( loaded )
        {
            switch ( item.getItemId())
            {
                case R.id.addNewPageBackground:
                    addNewPageHandler();
                    break;

                case R.id.saveBackground:
                    savePageHandler();
                    busHandler.requestMenuItem( new MenuItemEvent( item ) );
                    break;

                case R.id.deleteBackground:
                    deletePageHandler();
                    busHandler.requestMenuItem( new MenuItemEvent( item ) );
                    break;

                default:

            }
        }

        return (super.onOptionsItemSelected(item));
    }

    private void addNewPageHandler()
    {
        Page page = new Page( "page_" + fragmentList.size()  + ".json", new Date(), "", "" );

        try {
            fragmentList.add( PageFragment.newInstance(page) );
            pagerAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePageHandler()
    {
        try{

            JSON json = JSON.std;
            JSONComposer composer = json.with(JSON.Feature.PRETTY_PRINT_OUTPUT).composeString();
            ArrayComposer arrayComposer = composer.startArray();


            for( PageFragment pageFragment: fragmentList)
            {
                Page page = pageFragment.getPage();

                if( page != null )
                {
                    arrayComposer.startObject().put("fileName", page.getFileName() ).end();
                }
            }

            arrayComposer.end();
            String myJsonString = composer.finish().toString();

            File file = new File( this.getFilesDir(), "all_pages.json");
            saveTask.execute( myJsonString, file );
        }
        catch (Exception e )
        {
            Logging.print( "saving all_pages.json has an exception" + e.getMessage() );
        }
    }

    private void deletePageHandler()
    {

    }
}