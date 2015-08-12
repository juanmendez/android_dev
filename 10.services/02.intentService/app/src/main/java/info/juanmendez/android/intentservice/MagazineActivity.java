package info.juanmendez.android.intentservice;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Bus;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.ObjectGraph;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.helper.PageUtil;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.Page;
import info.juanmendez.android.intentservice.model.adapter.WebViewAdapter;
import info.juanmendez.android.intentservice.module.ActivityModule;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;
import info.juanmendez.android.intentservice.service.provider.table.SQLPage;

public class MagazineActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @Inject
    ArrayList<Page> pageList;

    @Inject
    protected DownloadProxy receiver;

    @Inject
    MagazineDispatcher dispatcher;

    @Inject
    protected Bus bus;

    ViewPager viewPager;
    WebViewAdapter adapter;
    ObjectGraph graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);


        MagazineApp app = (MagazineApp)getApplication();
        graph = app.getGraph().plus( new ActivityModule(this));
        graph.inject(this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new WebViewAdapter(this);
        viewPager.setAdapter(adapter);
    }

    public void inject( Object object ){
        graph.inject(object);
    }

    @Override
    public void onResume(){
        super.onResume();
        bus.register(this);

        Magazine mag =  dispatcher.getMagazine();

        if( mag.getId() > 0 && pageList.size() == 0 ){
            getSupportLoaderManager().initLoader(1, null,this);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Magazine magazine = dispatcher.getMagazine();

        CursorLoader cursorLoader = new CursorLoader( MagazineActivity.this,

                Uri.parse("content://" + BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider/pages"),
                new String[]{SQLPage.ID, SQLPage.POSITION, SQLPage.NAME, SQLPage.MAG_ID},
                SQLPage.MAG_ID + " = ?",
                new String[]{ Integer.toString(magazine.getId())},
                null );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor query) {

        pageList.clear();
        Page page;

        while( query.moveToNext())
        {
            page = PageUtil.fromCursor( query );
            pageList.add( page );
        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}