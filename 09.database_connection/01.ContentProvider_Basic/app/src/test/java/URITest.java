/**
 * Created by Juan on 6/29/2015.
 */

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import java.util.HashMap;

import info.juanmendez.android.db.contentprovider.BuildConfig;
import info.juanmendez.android.db.contentprovider.CountryProvider;

import static org.robolectric.Shadows.shadowOf;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest=Config.NONE)
public class URITest {
    
    @Test
    public void test_00(){

        final int allrows = 1;
        final int single_row = 2;
        final int search = 3;


        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH );
        matcher.addURI(CountryProvider.AUTHORITY, "elements", allrows );
        matcher.addURI(CountryProvider.AUTHORITY, "elements/#", single_row );

        matcher.addURI(CountryProvider.AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, search );
        matcher.addURI(CountryProvider.AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", search );
        matcher.addURI(CountryProvider.AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT, search );
        matcher.addURI(CountryProvider.AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", search );


        Uri uri = Uri.parse( "content://" + CountryProvider.AUTHORITY + "/" + SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/hello world" );


        final HashMap<String, String> SEARCH_PROJECTION_MAP;
        SEARCH_PROJECTION_MAP = new HashMap<String, String>();

        SEARCH_PROJECTION_MAP.put( SearchManager.SUGGEST_COLUMN_TEXT_1, "name as " + SearchManager.SUGGEST_COLUMN_TEXT_1 );
        SEARCH_PROJECTION_MAP.put( "_id", "id as _id" );

        /**
         * remember this type of match can return the content type being returned..
         * using contentProvider.getType();
         */
        switch ( matcher.match(uri))
        {
            case allrows: Log.print("all rows, doesn't need to appendWhere on sqliteQueryBuilder"); break;
            case single_row: sqLiteQueryBuilder.appendWhere( "id = " + uri.getPathSegments().get(1)); break;
            case search:
            Log.print(uri.getPathSegments());

            break;
            default: Log.print("no match");
        }

        sqLiteQueryBuilder.setTables( "countries");
        Log.print(sqLiteQueryBuilder.toString());
    }
}
