package info.juanmendez.android.searchinterface;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.SearchRecentSuggestions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Juan on 7/4/2015.
 */
public class MyCustomSuggestionProvider extends ContentProvider{
    static final int ALLROWS = 1;
    static final int SINGLE_ROW = 2;
    static final int SEARCH = 3;

    public static final String AUTHORITY = "info.juanmendez.android.searchinterface.MyCustomSuggestionProvider";

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        uriMatcher.addURI(AUTHORITY, "elements", ALLROWS);
        uriMatcher.addURI(AUTHORITY, "elements/#", SINGLE_ROW);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH );
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH );
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT, SEARCH );
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", SEARCH );
    }

    static final HashMap<String, String> SEARCH_PROJECTION_MAP;

    static{
        SEARCH_PROJECTION_MAP = new HashMap<String, String>();
        SEARCH_PROJECTION_MAP.put( OpenHelper.NAME, OpenHelper.NAME + " as " + SearchManager.SUGGEST_COLUMN_TEXT_1 );
        SEARCH_PROJECTION_MAP.put( OpenHelper.ID , OpenHelper.ID + " as " + BaseColumns._ID);
        SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, OpenHelper.ID + " as " +
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, OpenHelper.ID + " as " +
                SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);
    }

      private OpenHelper openHelper;

    @Override
    public boolean onCreate() {
        openHelper = new OpenHelper( getContext() );
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables( OpenHelper.TABLE );

        /**
         * remember this type of match can return the content type being returned..
         * using contentProvider.getType();
         */
        switch ( uriMatcher.match(uri))
        {

            case SINGLE_ROW:
                sqLiteQueryBuilder.appendWhere( OpenHelper.ID + "=" + uri.getPathSegments().get(1));
            break;
            case ALLROWS: break;
            case SEARCH:

                if (selectionArgs == null) {
                    throw new IllegalArgumentException(
                            "selectionArgs must be provided for the Uri: " + uri);
                }

                selectionArgs = new String[]{ "%" + selectionArgs[0] + "%"};

                sqLiteQueryBuilder.setProjectionMap( SEARCH_PROJECTION_MAP );
                break;
                default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        //SQLiteDirectCursorDriver: SELECT name as suggest_text_1, id as _id FROM countries WHERE (name LIKE "%Guat%") AND ( ?)
        //Cursor cursor = sqLiteQueryBuilder.query( db, projection, selection, selectionArgs, null, null, "" );
        Cursor cursor = sqLiteQueryBuilder.query( db, projection, selection, selectionArgs, null, null, "" );
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch ( uriMatcher.match(uri))
        {
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.juanmendez.elemental";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.juanmendez.elemental";
            case SEARCH:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException( "Unsuported URI: " + uri );
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        String nullColumnHack = null;

        long id = db.insert( OpenHelper.TABLE, nullColumnHack, values );


        if( id > -1 )
        {
            Uri insertedId = ContentUris.withAppendedId(uri, id);

            //notify from content resolver..
            getContext().getContentResolver().notifyChange( insertedId, null);
            return insertedId;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
