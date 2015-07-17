import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;
import org.robolectric.util.ActivityController;

import info.juanmendez.android.contentloader.BuildConfig;
import info.juanmendez.android.contentloader.CountryProvider;
import info.juanmendez.android.contentloader.Log;
import info.juanmendez.android.contentloader.MainActivity;
import info.juanmendez.android.contentloader.OpenHelper;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Juan on 7/5/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml")
public class LoaderTest
{
    private ContentResolver resolver;
    private ShadowContentResolver shadowResolver;
    private CountryProvider provider;
    private ActivityController controller;
    private MainActivity activity;
    private LoaderManager loaderManager;
    private LoadHolder loadHolder;

    public final Uri URILOC = Uri.parse( "content://" + CountryProvider.AUTHORITY + "/elements" );

    @Before
    public void before(){
        provider = new CountryProvider();
        resolver = RuntimeEnvironment.application.getContentResolver();
        shadowResolver = shadowOf( resolver );
        provider.onCreate();
        ShadowContentResolver.registerProvider( CountryProvider.AUTHORITY, provider );

        controller = Robolectric.buildActivity( MainActivity.class );
        activity = (MainActivity) controller.create().start().resume().visible().get();
        loaderManager =  activity.getSupportLoaderManager();
        loadHolder = new LoadHolder();
    }

    @Test
    public void testLoader()
    {
        loaderManager.initLoader( 0, null, loadHolder  );
       // loaderManager.restartLoader( 0, null, loadHolder );
    }


    public class LoadHolder implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            CursorLoader cursorLoader = new CursorLoader( activity, URILOC, new String[]{OpenHelper.ID, OpenHelper.NAME}, "", null, "" );
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.print( "completed!" );
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.print( "load reset!" + loader.toString() );
        }
    }
}
