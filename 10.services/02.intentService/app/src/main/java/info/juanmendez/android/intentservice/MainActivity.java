package info.juanmendez.android.intentservice;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.CursorLoader;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.ObjectGraph;
import info.juanmendez.android.intentservice.helper.DownloadProxy;
import info.juanmendez.android.intentservice.helper.PageUtil;
import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.Page;
import info.juanmendez.android.intentservice.module.ActivityModule;
import info.juanmendez.android.intentservice.service.page.PageAdapter;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;
import info.juanmendez.android.intentservice.service.provider.SQLPage;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    WebView webView;
    @Inject
    Magazine magazine;

    @Inject
    ArrayList<Page> pageList;

    @Inject
    PageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MagazineApp app = (MagazineApp)getApplication();
        ObjectGraph graph = app.getGraph().plus( new ActivityModule(this));
        graph.inject(this);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        getSupportLoaderManager().initLoader(1, null, this);
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
        CursorLoader cursorLoader = new CursorLoader( MainActivity.this,

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

        magazine.setStatus(MagazineStatus.DOWNLOADED);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}