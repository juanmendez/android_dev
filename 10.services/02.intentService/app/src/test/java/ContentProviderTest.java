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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.robolectric.Shadows.shadowOf;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.service.provider.DownloadHelper;
import info.juanmendez.android.intentservice.service.provider.PageBank;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", emulateSdk = 21 )
public class ContentProviderTest
{
    private ContentResolver resolver;
    private ShadowContentResolver shadowResolver;
    private PageBank provider;

    @Before
    public void prep(){
        provider = new PageBank();
        resolver = RuntimeEnvironment.application.getContentResolver();
        shadowResolver = shadowOf(resolver);

        provider.onCreate();
        ShadowContentResolver.registerProvider(PageBank.AUTHORITY, provider);
    }

    @Test
    public  void testContentProvider()
    {
        Uri uri = Uri.parse( "content://" + PageBank.AUTHORITY + "/downloads" );
        Uri result;

        ContentValues row = new ContentValues();
        row.put(DownloadHelper.VERSION, "2.22");
        row.put(DownloadHelper.DATETIME, DownloadHelper.SQLDATEFORMAT.format(new Date()));
        row.put(DownloadHelper.LOCATION, "/wherever/1.zip");
        result = resolver.insert(uri, row);

        row = new ContentValues();
        row.put(DownloadHelper.VERSION, "2.23");
        row.put(DownloadHelper.DATETIME, "2015-01-01 00:00:00");
        row.put(DownloadHelper.LOCATION, "/wherever/2.zip");
        result = resolver.insert(uri, row);

        uri = Uri.parse("content://" + PageBank.AUTHORITY + "/downloads/limit/" + 1 );
        Cursor query = resolver.query(uri,
                new String[]{DownloadHelper.ID, DownloadHelper.VERSION, DownloadHelper.DATETIME,DownloadHelper.LOCATION},
                null,
                null,
                DownloadHelper.DATETIME + " desc" );

        Log.print( "download", query );
    }
}
