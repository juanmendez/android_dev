package com.commonsware.android.frw.filesdemo;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.model.MenuItemEvent;
import com.commonsware.android.frw.filesdemo.model.Page;
import com.commonsware.android.frw.filesdemo.service.BusHandler;
import com.commonsware.android.frw.filesdemo.service.LoadTask;
import com.commonsware.android.frw.filesdemo.service.SaveTask;
import com.commonsware.android.frw.filesdemo.utils.Logging;
import com.fasterxml.jackson.jr.ob.JSON;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.io.File;
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

    @Bean
    SaveTask saveTask;

    @Bean
    LoadTask loadTask;

    Page _page;

    public static PageFragment newInstance( Page thisPage ) throws IOException {

        /**
         *
         * in AndroidAnnotations as regular way to inject parameters to Fragment,
         * they need to be native datatypes, or parcelable. For that reason, and because
         * this application uses jackson-jr, the new fragment is going to have injected a json object
         * and generated as string.
         */
        return PageFragment_.builder()._pageJson( Page.getJSONPage(thisPage, true )).build();
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

            //lets go and load the rest of the application..
            loadTask.execute( new File( getActivity().getFilesDir(), _page.getFileName() ) );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Subscribe
    public void actionEventSubscriber( ActionEvent event )
    {
        if( event.getFile().getName().equals(_page.getFileName()) )
        {
            if( event.getAction() == ActionEvent.ActionType.LOAD )
            {
                try {

                    if( event.getContent() != null && !event.getContent().isEmpty() )
                    {
                        _page = JSON.std.beanFrom(Page.class, event.getContent() );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        MenuItem menuItem = menuItemEvent.getMenuItem();

        switch( menuItem.getItemId() )
        {
            case R.id.saveBackground:
                saveHandler();
                break;

            case R.id.deleteBackground:

                break;
        }
    }

    @Override
    public void onDetach() {

        busHandler.unregister(this);
        super.onDetach();
    }

    private void saveHandler()
    {
        try{

            File file = new File( getActivity().getFilesDir(), _page.getFileName() );
            saveTask.execute( Page.getJSONPage(_page, true ), file );
        }
        catch (Exception e )
        {
            Logging.print( "saving pages.json has an exception" + e.getMessage() );
        }
    }

    public Page getPage()
    {
        return _page;
    }
}
