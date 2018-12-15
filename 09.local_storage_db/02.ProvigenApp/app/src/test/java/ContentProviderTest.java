import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import info.juanmendez.android.provigenapp.BuildConfig;
import info.juanmendez.android.provigenapp.CountryContract;
import info.juanmendez.android.provigenapp.CountryProvider;

import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Juan on 6/30/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, emulateSdk = 21, manifest="app/src/main/AndroidManifest.xml")
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

    @Test
    public  void test_02()
    {
        Uri content_uri = Uri.parse( "content://" + CountryProvider.AUTHORITY );
        Uri result;

        ContentValues row = new ContentValues();
        row.put(CountryContract.NAME, "Guatemala");
        result = resolver.insert(content_uri, row);
        print(  "path of new country " + result.getPath() );


        Cursor cursor = resolver.query(content_uri,
                new String[]{CountryContract.ID, CountryContract.NAME},
                null,
                null,
                null);

        print( "get all countries", cursor);

        row = new ContentValues();
        row.put(CountryContract.NAME, "Republica de Guatemala");

        resolver.update(content_uri, row, CountryContract.NAME + "=?", new String[]{"Guatemala"});

        resolver.query(  Uri.withAppendedPath( content_uri, "1" ), null, "", null, "" );

        cursor = resolver.query(content_uri,
                new String[]{CountryContract.ID, CountryContract.NAME},
                null,
                null,
                null);

        print("See results updated!", cursor);

        //lets remove Guatemala
        resolver.delete(content_uri, CountryContract.NAME + "=?", new String[]{ "Republica de Guatemala"} );

        cursor = resolver.query(content_uri,
                new String[]{CountryContract.ID, CountryContract.NAME},
                null,
                null,
                null);

        assertTrue( "There are items!", cursor.getCount() == 0 );

    }

    public void print( Cursor result )
    {
        while( result.moveToNext() )
        {
            print( "id: " + result.getInt(result.getColumnIndexOrThrow( CountryContract.ID )) + ", name: " + result.getString(result.getColumnIndexOrThrow( CountryContract.NAME )));
        }
    }

    public void print( String message, Cursor result )
    {
        print( message );
        print( result );
    }

    public void print( String content )
    {
        System.out.println( content );
    }

}
