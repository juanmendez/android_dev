package info.juanmendez.android.intentservice.ui.magazine;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.squareup.otto.Bus;

import java.util.ArrayList;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.helper.MVPUtils;
import info.juanmendez.android.intentservice.helper.PageUtil;
import info.juanmendez.android.intentservice.model.adapter.WebViewAdapter;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.Page;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;
import info.juanmendez.android.intentservice.service.provider.table.SQLPage;
import info.juanmendez.android.intentservice.ui.MagazineActivity;
import info.juanmendez.android.intentservice.ui.listmagazine.IListMagazinesView;

/**
 * Created by Juan on 8/20/2015.
 */
public class MagazinePresenter implements IMagazinePresenter {

    Activity activity;

    @Inject
    ArrayList<Page> pageList;

    @Inject
    MagazineDispatcher dispatcher;

    @Inject
    protected Bus bus;

    WebViewAdapter adapter;
    IMagazineView view;

    public MagazinePresenter(Activity activity ){
        this.activity = activity;
        view = MVPUtils.getView( activity, IMagazineView.class );
        view.inject(this);

        adapter = new WebViewAdapter(activity);
        view.setAdapter(adapter);
    }

    @Override
    public void pause() {
        bus.unregister(this);
    }

    @Override
    public void resume() {
        bus.register(this);

        Magazine mag =  dispatcher.getMagazine();

        if( mag.getId() > 0 && pageList.size() == 0 ){

            activity.getLoaderManager().initLoader(1, null,this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Magazine magazine = dispatcher.getMagazine();

        CursorLoader cursorLoader = new CursorLoader( activity,

                Uri.parse("content://" + BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider/pages"),
                new String[]{SQLPage.ID, SQLPage.POSITION, SQLPage.NAME, SQLPage.MAG_ID},
                SQLPage.MAG_ID + " = ?",
                new String[]{ Integer.toString(magazine.getId())},
                null );

        return cursorLoader;

    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor query) {

        pageList.clear();
        Page page;

        while( query.moveToNext())
        {
            page = PageUtil.fromCursor(query);
            pageList.add( page );
        }

        query.close();

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }
}
