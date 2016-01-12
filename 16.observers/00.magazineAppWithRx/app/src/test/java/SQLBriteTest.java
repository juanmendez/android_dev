import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

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
import info.juanmendez.android.intentservice.helper.MagazineUtil;
import info.juanmendez.android.intentservice.helper.PageUtil;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;
import info.juanmendez.android.intentservice.service.provider.table.SQLMagazine;
import info.juanmendez.android.intentservice.ui.MagazineApp;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="src/main/AndroidManifest.xml", sdk = 21 )
public class SQLBriteTest
{
    private ContentResolver resolver;
    private ShadowContentResolver shadowResolver;
    private MagazineProvider provider;
    private MagazineApp app;
    private SqlBrite sqlBrite;
    private BriteContentResolver briteContentResolver;

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

       sqlBrite = SqlBrite.create();
       briteContentResolver = sqlBrite.wrapContentProvider( resolver );
    }

    @Test
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

        Observable<SqlBrite.Query> queryObservable = briteContentResolver.createQuery(uri, new String[]
                        {SQLMagazine.ID, SQLMagazine.ISSUE,
                                SQLMagazine.TITLE,
                                SQLMagazine.LOCATION,
                                SQLMagazine.FILE_LOCATION,
                                SQLMagazine.DATETIME,
                                SQLMagazine.STATUS},
                null,
                null,
                SQLMagazine.ISSUE + " desc", false);

        /**
         * prints
         * Magazine( issue: 2.23, location: /wherever/2.zip, title: null, file_location: null )
         * Magazine( issue: 2.22, location: /wherever/1.zip, title: null, file_location: null )
         */
        queryObservable
                .map(query -> {
                                ArrayList<Magazine> list = new ArrayList<>();

                                Cursor cursor = query.run();
                                while (cursor.moveToNext()) {
                                    list.add(MagazineUtil.fromCursor(cursor));
                                }

                                return list;
                })
                .subscribe(thoseMagazines -> {
                    for (Magazine m : thoseMagazines) {
                        Log.print(m.toString());
                    }
                });
    }
}