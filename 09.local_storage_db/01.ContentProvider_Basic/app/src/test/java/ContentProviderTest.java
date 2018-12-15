import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import info.juanmendez.android.db.contentprovider.BuildConfig;
import info.juanmendez.android.db.contentprovider.CountryProvider;
import info.juanmendez.android.db.contentprovider.OpenHelper;

import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Juan on 7/2/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml")
public class ContentProviderTest {
    
    private ContentResolver resolver;
    private ShadowContentResolver shadowResolver;
    private CountryProvider provider;


    @Before
    public void  prep(){

        provider = new CountryProvider();
        resolver = RuntimeEnvironment.application.getContentResolver();
        shadowResolver = shadowOf(resolver);
        provider.onCreate();
        ShadowContentResolver.registerProvider( CountryProvider.AUTHORITY, provider);
    }

    //works on testing a dataprovider..
    @Test
    public  void testContentProvider()
    {
        Uri content_uri = Uri.parse( "content://" + CountryProvider.AUTHORITY + "/elements/" );
        Uri result;

        ContentValues row = new ContentValues();
        row.put(OpenHelper.NAME, "Guatemala");
        result = resolver.insert(content_uri, row);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "El Salvador");
        result = resolver.insert(content_uri, row);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Honduras");
        result = resolver.insert(content_uri, row);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Nicaragua");
        result = resolver.insert(content_uri, row);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Costa Rica");
        result = resolver.insert(content_uri, row);

        Log.print(result.getPath());


        Cursor cursor = resolver.query(content_uri,
                new String[]{OpenHelper.ID, OpenHelper.NAME},
                null,
                null,
                null);

        Log.print(cursor);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Republica de Guatemala");

        resolver.update(content_uri, row, OpenHelper.NAME + "=?", new String[]{"Guatemala"});

        cursor = resolver.query(content_uri,
                new String[]{OpenHelper.ID, OpenHelper.NAME},
                null,
                null,
                null);

        Log.print("See results updated!", cursor);

        //lets remove Guatemala
        resolver.delete(content_uri, OpenHelper.NAME + "=?", new String[]{ "Republica de Guatemala"} );

        cursor = resolver.query(content_uri,
                new String[]{OpenHelper.ID, OpenHelper.NAME},
                null,
                null,
                null);

        Log.print( "query result after deleting first item", cursor );

        content_uri = Uri.parse( "content://" + CountryProvider.AUTHORITY + "/" + SearchManager.SUGGEST_URI_PATH_QUERY  + "/G");
        cursor = resolver.query(content_uri,
                new String[]{OpenHelper.ID, OpenHelper.NAME},
                null,
                null,
                null);

        while( cursor.moveToNext() )
        {
            Log.print( cursor.getInt(cursor.getColumnIndexOrThrow( "_id" )) +
                    " " +
                    cursor.getString(cursor.getColumnIndexOrThrow( "_name" ))
            );
        }
    }
}