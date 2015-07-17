package com.commonsware.android.frw.filesdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.commonsware.android.frw.filesdemo.databinding.PageLayoutBinding;
import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.model.MenuItemEvent;
import com.commonsware.android.frw.filesdemo.model.Page;
import com.commonsware.android.frw.filesdemo.service.BusHandler;
import com.commonsware.android.frw.filesdemo.service.FileTask;
import com.commonsware.android.frw.filesdemo.utils.Logging;
import com.fasterxml.jackson.jr.ob.JSON;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.TextChange;

import java.io.File;
import java.io.IOException;

/**
 * Created by Juan on 5/30/2015.
 */
@EFragment
public class PageFragment extends Fragment {
    @FragmentArg
    String _pageJson;

    @Bean
    BusHandler busHandler;

    @Bean
    FileTask fileTask;

    Page _page;
    PageLayoutBinding pageLayoutBinding;

    Boolean loaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_layout, container, false);
        pageLayoutBinding = PageLayoutBinding.bind(view);

        return view;
    }

    public static PageFragment newInstance(Page thisPage) throws IOException {

        /**
         *
         * in AndroidAnnotations as regular way to inject parameters to Fragment,
         * they need to be native datatypes, or parcelable. For that reason, and because
         * this application uses jackson-jr, the new fragment is going to have injected a json object
         * and generated as string.
         */
        return PageFragment_.builder()._pageJson(Page.getJSONPage(thisPage)).build();
    }

    /**
     * 1.
     * After the fragment is created, the json generated parameter is parsed and mapped using
     * Page.class.
     */
    @AfterViews
    void AfterViews() {
        try {
            _page = JSON.std.beanFrom(Page.class, _pageJson);

            //lets go and load the rest of the application..
            busHandler.register(this);
            fileTask.load_execute( _page.getFileName() );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Subscribe
    public void actionEventSubscriber(ActionEvent event) {
        if (event.getFile().getName().equals(_page.getFileName())) {
            if (event.getAction() == ActionEvent.ActionType.LOAD) {
                try {

                    if (event.getContent() != null ) {
                        Page _temp = JSON.std.beanFrom(Page.class, event.getContent());
                        _page.setContent( _temp.getContent() );
                        _page.setTitle( _temp.getTitle() );
                        _page.setDateCreated( _temp.getDateCreated() );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally{
                    pageLayoutBinding.setPage(_page);
                }
            } else if (event.getAction() == ActionEvent.ActionType.DELETE) {
                busHandler.requestEvent(new ActionEvent(ActionEvent.ActionType.DELETE_CONFIRMED, event.getFile()));
            }
        }
    }

    /**
     * each fragment needs to subscribe to BusHandler to find out
     * when a new menuItem has been selected away from main Activity.
     * This also requires to only have the present fragment to catch
     * those event so we use this event handler to find out when the
     * fragment is present and when it goes away.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (busHandler != null) {
            if (isVisibleToUser) {
                if (_page != null)
                    _page.setVisible(true);
            } else {
                if (_page != null)
                    _page.setVisible(false);
            }
        }
    }

    @Subscribe
    public void menuItemSubscriber(MenuItemEvent menuItemEvent) {
        MenuItem menuItem = menuItemEvent.getMenuItem();

        switch (menuItem.getItemId()) {
            case R.id.saveBackground:
                saveHandler();
                break;

            case R.id.deleteBackground:
                deleteHandler();
                break;
        }
    }

    @Override
    public void onDetach() {

        busHandler.unregister(this);
        super.onDetach();
    }

    private void saveHandler() {
        try {
            fileTask.save_execute(Page.getJSONPage(_page), _page.getFileName());
        } catch (Exception e) {
            Logging.print("saving pages.json has an exception" + e.getMessage());
        }
    }

    private void deleteHandler() {

        if( _page != null && _page.getVisible() )
        {
            fileTask.delete_execute(_page.getFileName());
        }
    }

    public Page getPage() {
        return _page;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true);
    }

    @TextChange
    void titleTextChanged(TextView titleText) {
        _page.setTitle(titleText.getText().toString());
    }

    @TextChange
    void contentTextChanged(TextView contentText) {
        _page.setContent(contentText.getText().toString());
    }
}
