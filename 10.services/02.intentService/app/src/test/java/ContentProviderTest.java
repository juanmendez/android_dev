import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

import junit.framework.Assert;

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

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.MagazineApp;
import info.juanmendez.android.intentservice.helper.MagazineUtil;
import info.juanmendez.android.intentservice.helper.PageUtil;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.Page;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;
import info.juanmendez.android.intentservice.service.provider.table.SQLMagazine;
import info.juanmendez.android.intentservice.service.provider.table.SQLPage;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", emulateSdk = 21 )
public class ContentProviderTest
{
    private ContentResolver resolver;
    private ShadowContentResolver shadowResolver;
    private MagazineProvider provider;
    private MagazineApp app;

    static{
        ShadowLog.stream = System.out;
    }

   @Before
    public void prep(){

         app = (MagazineApp) RuntimeEnvironment.application;
        provider = new MagazineProvider();
        resolver = RuntimeEnvironment.application.getContentResolver();
        shadowResolver = shadowOf(resolver);
        provider.onCreate();
        ShadowContentResolver.registerProvider(MagazineProvider.AUTHORITY, provider);
    }

    //@Test
    public  void testContentProvider()
    {
        ArrayList<Magazine> magazines = new ArrayList<Magazine>();
        Uri uri = Uri.parse( "content://" + MagazineProvider.AUTHORITY + "/magazines" );
        Uri result;

        ContentValues row = new ContentValues();
        row.put(SQLMagazine.ISSUE, "2.22");
        row.put(SQLMagazine.DATETIME, PageUtil.TableUtils.dateFormat(new Date()));
        row.put(SQLMagazine.LOCATION, "/wherever/1.zip");
        result = resolver.insert(uri, row);

        Assert.assertEquals( result.getPath(), uri.getPath() + "/1");

        row = new ContentValues();
        row.put(SQLMagazine.ISSUE, "2.23");
        row.put(SQLMagazine.DATETIME, "2015-01-01 00:00:00");
        row.put(SQLMagazine.LOCATION, "/wherever/2.zip");
        result = resolver.insert(uri, row);

        Assert.assertEquals(result.getPath(), uri.getPath() + "/2");

        Cursor query = resolver.query(uri,
                new String[]{SQLMagazine.ID, SQLMagazine.ISSUE, SQLMagazine.TITLE, SQLMagazine.LOCATION,
                        SQLMagazine.FILE_LOCATION,
                        SQLMagazine.DATETIME,
                        SQLMagazine.STATUS},
                null,
                null,
                SQLMagazine.ISSUE + " desc");

        while( query.moveToNext() )
        {
            magazines.add(MagazineUtil.fromCursor(query));
        }

        for( Magazine m: magazines ){
            Log.print( magazines.toString() );
        }
    }

    @Test
    public void testPages(){
        Uri uri = Uri.parse( "content://" + MagazineProvider.AUTHORITY + "/pages" );
        ArrayList<Page> pages = new ArrayList<Page>();
        Page page = new Page();
        page.setPosition(1);
        page.setMagId(1);
        page.setName("bootstrap.html");
        pages.add(page);

        page = new Page();
        page.setPosition(2);
        page.setMagId(1);
        page.setName("jquery.html");
        pages.add(page);

        page = new Page();
        page.setPosition(3);
        page.setMagId(1);
        page.setName("knockout.html");
        pages.add(page);

        page = new Page();
        page.setPosition(3);
        page.setMagId(1);
        page.setName("angularjs.html");
        pages.add(page);

        Uri result;

        for( int i = 0; i < pages.size(); i++ ){

            ContentValues row = PageUtil.toContentValues( pages.get(i));
            try {
                result = resolver.insert(uri, row);

                if( result != null )
                {
                    Assert.assertTrue( "path is valid " + result.getPath(), MagazineProvider.uriMatcher.match( result ) == MagazineProvider.SINGLE_PAGE );
                }

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }

        Cursor query = resolver.query(uri,
                new String[]{SQLPage.ID, SQLPage.MAG_ID, SQLPage.NAME, SQLPage.POSITION },
                null,
                null,
                SQLPage.POSITION + " desc");

        pages.clear();

        while( query.moveToNext() )
        {
            page = PageUtil.fromCursor(query);

            Log.print( page.toString() );
            pages.add( page );
        }
    }


    //@Test
    public void testLimit(){
        Uri uri = Uri.parse("content://" + MagazineProvider.AUTHORITY + "/magazines/limit/" + 1 );
        Cursor query = resolver.query(uri,
                new String[]{SQLMagazine.ID, SQLMagazine.ISSUE, SQLMagazine.DATETIME, SQLMagazine.LOCATION},
                null,
                null,
                SQLMagazine.ISSUE + " desc" );

        Log.print("magazines", query);
    }

    //@Test
    public void testMagazineAndPages(){
        Uri magazineURI = Uri.parse( "content://" + MagazineProvider.AUTHORITY + "/magazines" );
        Uri pagesURI = Uri.parse( "content://" + MagazineProvider.AUTHORITY + "/pages" );
        Uri result;

        ContentValues row = new ContentValues();
        row.put(SQLMagazine.ISSUE, "2.22");
        row.put(SQLMagazine.LOCATION, "/wherever/1.zip");
        row.put(SQLMagazine.DATETIME, PageUtil.TableUtils.dateFormat(new Date()));
        result = resolver.insert(magazineURI, row);

        int lastMagazineID = Integer.parseInt(result.getLastPathSegment());

        if( lastMagazineID >= 0 ){

            Cursor query = shadowResolver.query(magazineURI,
                    new String[]{SQLMagazine.ID, SQLMagazine.ISSUE, SQLMagazine.DATETIME, SQLMagazine.LOCATION},
                    null,
                    null,
                    null );

            Log.print( "magazines", query );

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
        }

    }
}
