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
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.Date;

import static org.robolectric.Shadows.shadowOf;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.service.provider.SQLGlobals;
import info.juanmendez.android.intentservice.service.provider.SQLMagazine;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;
import info.juanmendez.android.intentservice.service.provider.SQLPage;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", emulateSdk = 21 )
public class ContentProviderTest
{
    private ContentResolver resolver;
    private ShadowContentResolver shadowResolver;
    private MagazineProvider provider;

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
        //you other setup here
    }

   @Before
    public void prep(){
        provider = new MagazineProvider();
        resolver = RuntimeEnvironment.application.getContentResolver();
        shadowResolver = shadowOf(resolver);

        provider.onCreate();
        ShadowContentResolver.registerProvider(MagazineProvider.AUTHORITY, provider);
    }

    //@Test
    public  void testContentProvider()
    {
        Uri uri = Uri.parse( "content://" + MagazineProvider.AUTHORITY + "/magazines" );
        Uri result;

        ContentValues row = new ContentValues();
        row.put(SQLMagazine.VERSION, "2.22");
        row.put(SQLMagazine.DATETIME, SQLGlobals.dateFormat(new Date()));
        row.put(SQLMagazine.LOCATION, "/wherever/1.zip");
        result = resolver.insert(uri, row);

        row = new ContentValues();
        row.put(SQLMagazine.VERSION, "2.23");
        row.put(SQLMagazine.DATETIME, "2015-01-01 00:00:00");
        row.put(SQLMagazine.LOCATION, "/wherever/2.zip");
        result = resolver.insert(uri, row);

        uri = Uri.parse("content://" + MagazineProvider.AUTHORITY + "/magazines/limit/" + 1 );
        Cursor query = resolver.query(uri,
                new String[]{SQLMagazine.ID, SQLMagazine.VERSION, SQLMagazine.DATETIME, SQLMagazine.LOCATION},
                null,
                null,
                SQLMagazine.DATETIME + " desc" );

        Log.print("magazines", query);
    }

    @Test
    public void testMagazineAndPages(){
        Uri magazineURI = Uri.parse( "content://" + MagazineProvider.AUTHORITY + "/magazines" );
        Uri pagesURI = Uri.parse( "content://" + MagazineProvider.AUTHORITY + "/pages" );
        Uri result;

        ContentValues row = new ContentValues();
        row.put(SQLMagazine.VERSION, "2.22");
        row.put(SQLMagazine.LOCATION, "/wherever/1.zip");
        row.put(SQLMagazine.DATETIME, SQLGlobals.dateFormat(new Date()));
        result = resolver.insert(magazineURI, row);

        int lastMagazineID = Integer.parseInt(result.getLastPathSegment());

        if( lastMagazineID >= 0 ){

            Cursor query = shadowResolver.query(magazineURI,
                    new String[]{SQLMagazine.ID, SQLMagazine.VERSION, SQLMagazine.DATETIME, SQLMagazine.LOCATION},
                    null,
                    null,
                    null );

            ArrayList<String> files = new ArrayList<String>(){{
                add("page1.html");
                add("page2.html");
                add("page3.html");
                add("page4.html");
                add("page5.html");
            }};


            for( String file: files ){

                ContentValues c = new ContentValues();
                c.put(SQLPage.MAG_ID, lastMagazineID );
                c.put( SQLPage.NAME, file );

                shadowResolver.insert( pagesURI, c );
            }


            query = shadowResolver.query(pagesURI,
                    new String[]{SQLPage.ID, SQLPage.MAG_ID, SQLPage.NAME},
                    null,
                    null,
                    null );

            Log.print( "pages", query );

        }

    }
}
