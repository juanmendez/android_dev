package com.commonsware.android.frw.filesdemo;

import android.os.Build;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.model.FragmentModel;
import com.commonsware.android.frw.filesdemo.model.MenuItemEvent;
import com.commonsware.android.frw.filesdemo.model.Page;
import com.commonsware.android.frw.filesdemo.model.PagerAdapter;
import com.commonsware.android.frw.filesdemo.service.BusHandler;
import com.commonsware.android.frw.filesdemo.service.FileTask;
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

@EActivity(R.layout.main)
@OptionsMenu(R.menu.actions)
public class MainActivity extends AppCompatActivity {

    @ViewById
    ViewPager vpPager;

    @ViewById
    PagerSlidingTabStrip tabStrip;

    @Bean
    FileTask fileTask;

    @Bean
    BusHandler busHandler;

    FragmentModel fragmentModel;
    PagerAdapter pagerAdapter;
    List<PageFragment> fragmentList;

    @Override
    public void onPause() {
        super.onPause();
        busHandler.unregister(this);
    }

    @AfterViews
    void afterViews() {
        fragmentModel = (FragmentModel) getFragmentManager().findFragmentByTag("fragment_model");

        if (fragmentModel == null) {
            fragmentModel = new FragmentModel();
            getFragmentManager().beginTransaction().add(fragmentModel, "fragment_model").commit();
        }

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(buildPolicy());
        }

        busHandler.register(this);

        fragmentList = fragmentModel.getFragmentList();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        vpPager.setAdapter(pagerAdapter);
        //tabStrip.setViewPager(vpPager);

        vpPager.setOffscreenPageLimit(3);
        vpPager.setClipToPadding(false);
        vpPager.setPageMargin(12);


        //buildTask.execute();
        if (!fragmentModel.getLoaded())
            fileTask.load_execute((new File(getFilesDir(), "all_pages.json")));
    }

    private StrictMode.ThreadPolicy buildPolicy() {
        return (new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog()
                .build());
    }

    @Subscribe
    public void actionEventSubscriber(ActionEvent event) {
        if (event.getFile().getName().equals("all_pages.json")) {
            if (event.getAction() == ActionEvent.ActionType.LOAD) {
                /**
                 * after loading, lets use the new list  to generate pages for pagerviewer,
                 * and of course, through the adapter. Also lets not forget to update the adapter
                 * to show the new pages!
                 */
                try {
                    fragmentModel.setLoaded(true);
                    List<Page> list;
                    list = JSON.std.listOfFrom(Page.class, event.getContent());

                    for (Page page : list) {
                        fragmentList.add(PageFragment.newInstance(page));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    pagerAdapter.notifyDataSetChanged();
                }
            } else if (event.getAction() == ActionEvent.ActionType.SAVE) {
                pagerAdapter.notifyDataSetChanged();
            }
        } else if (event.getAction() == ActionEvent.ActionType.DELETE_CONFIRMED) {
            PageFragment pageFragment;
            int i, length = fragmentList.size();

            for (i = 0; i < length; i++) {
                pageFragment = fragmentList.get(i);

                if (pageFragment.getPage().getFileName().equals(event.getFile().getName())) {

                    if (i == length - 1)
                        vpPager.setCurrentItem(i - 1);
                    else if (i == 0 && length > 1)
                        vpPager.setCurrentItem(1);

                    fragmentList.remove(i);
                    savePageHandler();
                    break;
                }
            }
        }
    }

    /**
     * We want fragments withing PagerViewer to know when any selected menuItem
     * has been picked. So BusHandler does that for us, all we need is to assign
     * a new MenuItemEvent. I wanted to use directly the MenuItem instance but there was a precompiled error
     * from Otto telling that the object is not from a concrete class, so I decided to embed it.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (fragmentModel.getLoaded()) {
            switch (item.getItemId()) {
                case R.id.addNewPageBackground:
                    addNewPageHandler();
                    break;

                case R.id.saveBackground:
                    savePageHandler();
                    busHandler.requestMenuItem(new MenuItemEvent(item));
                    break;

                case R.id.deleteBackground:
                    busHandler.requestMenuItem(new MenuItemEvent(item));
                    break;

                default:
                    Logging.print("couldn't find action for " + item.getItemId());
            }
        }

        return (super.onOptionsItemSelected(item));
    }

    private void addNewPageHandler() {
        Page page = new Page("page_" + fragmentList.size() + ".json", new Date(), "", "", false);

        try {
            fragmentList.add(PageFragment.newInstance(page));
            pagerAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePageHandler() {
        try {

            JSON json = JSON.std;
            JSONComposer composer = json.with(JSON.Feature.PRETTY_PRINT_OUTPUT).composeString();
            ArrayComposer arrayComposer = composer.startArray();


            for (PageFragment pageFragment : fragmentList) {
                Page page = pageFragment.getPage();

                if (page != null) {
                    arrayComposer.startObject().put("fileName", page.getFileName())
                            .put("visible", page.getVisible()).end();
                }
            }

            arrayComposer.end();
            String myJsonString = composer.finish().toString();

            File file = new File(this.getFilesDir(), "all_pages.json");
            fileTask.save_execute(myJsonString, file);
        } catch (Exception e) {
            Logging.print("saving all_pages.json has an exception" + e.getMessage());
        }
    }

    private void deletePageHandler() {
        Page page;

        for (PageFragment pageFragment : fragmentList) {
            page = pageFragment.getPage();

            if (page.getVisible() == true) {
                Logging.print("we should delete this file! " + page.getFileName());
            }
        }
    }
}