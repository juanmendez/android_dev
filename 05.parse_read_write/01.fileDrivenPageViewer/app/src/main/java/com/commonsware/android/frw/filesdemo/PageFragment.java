package com.commonsware.android.frw.filesdemo;

import android.support.v4.app.Fragment;

import com.commonsware.android.frw.filesdemo.model.MenuItemEvent;
import com.commonsware.android.frw.filesdemo.model.Page;
import com.commonsware.android.frw.filesdemo.service.BusHandler;
import com.commonsware.android.frw.filesdemo.utils.Logging;
import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONComposer;
import com.fasterxml.jackson.jr.ob.comp.ObjectComposer;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.io.IOException;

/**
 * Created by Juan on 5/30/2015.
 */
@EFragment(R.layout.page_layout)
public class PageFragment extends Fragment
{
    @FragmentArg
    String _pageJson;

    @Bean
    BusHandler busHandler;

    Page _page;

    public static PageFragment newInstance( Page thisPage ) throws IOException {

        /**
         *
         * in AndroidAnnotations as regular way to inject parameters to Fragment,
         * they need to be native datatypes, or parcelable. For that reason, and because
         * this application uses jackson-jr, the new fragment is going to have injected a json object
         * and generated as string.
         */
        JSON json = JSON.std;
        JSONComposer composer = json.with(JSON.Feature.PRETTY_PRINT_OUTPUT).composeString();
        ObjectComposer objectComposer = composer.startObject();
        objectComposer.put( "title", thisPage.getTitle() ).
                put("dateCreated", thisPage.getDateCreated().getTime()).
                put("fileName", thisPage.getFileName()).
                put("content", thisPage.getContent() ).end();

        return PageFragment_.builder()._pageJson(composer.finish().toString()).build();
    }

    /**
     * 1.
     * After the fragment is created, the json generated parameter is parsed and mapped using
     * Page.class.
     */
    @AfterInject
    void afterInject()
    {
        try {
            _page = JSON.std.beanFrom( Page.class, _pageJson );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * each fragment needs to subscribe to BusHandler to find out
     * when a new menuItem has been selected away from main Activity.
     * This also requires to only have the present fragment to catch
     * those event so we use this event handler to find out when the
     * fragment is present and when it goes away.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if( busHandler != null )
        {
            if (isVisibleToUser)
            {
                busHandler.register(this);
            }
            else
            {
                busHandler.unregister(this);
            }
        }
    }

    @Subscribe
    public void menuItemSubscriber( MenuItemEvent menuItemEvent ) {
        Logging.print(menuItemEvent.getMenuItem().toString());
    }

    public Page getPage()
    {
        return _page;
    }
}
