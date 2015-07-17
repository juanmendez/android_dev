package info.juanmendez.android.db.contentprovider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Juan on 6/29/2015.
 */
public class CountryProvider extends ContentProvider
{
    static final int ALLROWS = 1;
    static final int SINGLE_ROW = 2;
    static final int SEARCH = 3;

    public static final String AUTHORITY = "info.juanmendez.android.db.mycontentprovider";

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        uriMatcher.addURI(AUTHORITY, "elements", ALLROWS);
        uriMatcher.addURI(AUTHORITY, "elements#", SINGLE_ROW);

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
    }


    private OpenHelper openHelper;

    @Override
    public boolean onCreate() {

        openHelper = new OpenHelper( getContext() );
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int allrows = 1;
        final int SINGLE_ROW = 2;

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
            case SEARCH:
                List<String>list = uri.getPathSegments();

                if( list.size()>=2)
                {
                    sqLiteQueryBuilder.appendWhere( OpenHelper.NAME + " LIKE \"%" + list.get(1) + "%\"" );
                }

                sqLiteQueryBuilder.setProjectionMap( SEARCH_PROJECTION_MAP );
                break;
        }

        Cursor cursor = sqLiteQueryBuilder.query( db, projection, selection, selectionArgs, null, null, sortOrder );
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
        SQLiteDatabase db = openHelper.getWritableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables( OpenHelper.TABLE );
        
        switch ( uriMatcher.match(uri))
        {
            case SINGLE_ROW:
                String temp = OpenHelper.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
        }

        if( selection == null )
            selection = "1";


        int deleteCount = db.delete( OpenHelper.TABLE, selection, selectionArgs );
        //notify from content resolver..
        getContext().getContentResolver().notifyChange( uri, null);
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = openHelper.getWritableDatabase();

        switch ( uriMatcher.match(uri))
        {
            case SINGLE_ROW:
                String temp = OpenHelper.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
        }

        int updateCount = db.update(OpenHelper.TABLE, values, selection, selectionArgs);
        //notify from content resolver..
        getContext().getContentResolver().notifyChange( uri, null);
        return updateCount;
    }
}
