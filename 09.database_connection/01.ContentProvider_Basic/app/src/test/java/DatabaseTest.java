import android.content.ContentUris;
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

import info.juanmendez.android.db.contentprovider.BuildConfig;
import info.juanmendez.android.db.contentprovider.OpenHelper;

import static org.junit.Assert.assertEquals;

/**
 * Created by Juan on 7/2/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE)
public class DatabaseTest {
    OpenHelper openHelper;
    static final String AUTHORITY = "info.juanmendez.android.db.mycontentprovider";

    @Before
    public void prep_0() {
        openHelper = new OpenHelper(RuntimeEnvironment.application.getApplicationContext());
    }

    @Test
    public void test() {
        //oncreate
        write();
        Cursor result = query(Uri.parse("content://" + AUTHORITY + "/elements/4"),
                new String[]{OpenHelper.ID, OpenHelper.NAME},
                null,
                null,
                null);

        Log.print(result);
        assertEquals(result.getCount(), 1);

        int deletedItems = delete(Uri.parse("content://" + AUTHORITY + "/elements/"), OpenHelper.NAME + "=?", new String[]{"Nicaragua"});
        Log.print("items deleted = " + deletedItems);

        ContentValues row = new ContentValues();
        row.put(OpenHelper.NAME, "Republica Bolivariana de Venezuela");

        int countItems = update(Uri.parse("content://" + AUTHORITY + "/elements/"), row, OpenHelper.NAME + "=?", new String[]{"Venezuela"});
        Log.print("number of items updated " + countItems);

        result = query(Uri.parse("content://" + AUTHORITY + "/elements"),
                new String[]{OpenHelper.ID, OpenHelper.NAME},
                null,
                null,
                null);

        //assertTrue( "there is more than one country", result.getCount()>1 );
        Log.print(result);
    }

    private void write() {
        Uri content_uri = Uri.parse("content://" + AUTHORITY + "/elements/");
        Uri result;

        ContentValues row = new ContentValues();
        row.put(OpenHelper.NAME, "Guatemala");
        result = insert(content_uri, row);
        Log.print(result.getPath());

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Uruguay");
        result = insert(content_uri, row);
        Log.print(result.getPath());

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Nicaragua");
        result = insert(content_uri, row);
        Log.print(result.getPath());

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Ecuador");
        result = insert(content_uri, row);
        Log.print(result.getPath());

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Venezuela");
        result = insert(content_uri, row);
        Log.print(result.getPath());
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int allrows = 1;
        final int single_row = 2;

        SQLiteDatabase db = openHelper.getWritableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(OpenHelper.TABLE);

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "elements", allrows);
        matcher.addURI(AUTHORITY, "elements/#", single_row);

        /**
         * remember this type of match can return the content type being returned..
         * using contentProvider.getType();
         */
        switch (matcher.match(uri)) {
            case allrows:
                Log.print("all rows, doesn't need to appendWhere on sqliteQueryBuilder");
                break;
            case single_row:
                sqLiteQueryBuilder.appendWhere(OpenHelper.ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                Log.print("no match");
        }

        Cursor cursor = sqLiteQueryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(OpenHelper.TABLE);

        final int allrows = 1;
        final int single_row = 2;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "elements", allrows);
        matcher.addURI(AUTHORITY, "elements/#", single_row);


        switch (matcher.match(uri)) {
            case single_row:
                String temp = OpenHelper.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
        }

        if (selection == null)
            selection = "1";


        int deleteCount = db.delete(OpenHelper.TABLE, selection, selectionArgs);
        //notify from content resolver..
        return deleteCount;
    }


    Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        String nullColumnHack = null;

        long id = db.insert(OpenHelper.TABLE, nullColumnHack, values);


        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(uri, id);

            return insertedId;
        }

        return null;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = openHelper.getWritableDatabase();

        final int allrows = 1;
        final int single_row = 2;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "elements", allrows);
        matcher.addURI(AUTHORITY, "elements/#", single_row);


        switch (matcher.match(uri)) {
            case single_row:
                String temp = OpenHelper.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
        }

        int updateCount = db.update(OpenHelper.TABLE, values, selection, selectionArgs);
        //notify from content resolver..
        return updateCount;
    }
}
