package info.juanmendez.android.intentservice.service.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Juan on 7/20/2015.
 */
public class DownloadCrud implements CrudProvider {

    DownloadHelper helper;
    Context context;

    public DownloadCrud( Context context ){
        this.context = context;
        this.helper = new DownloadHelper( context );
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables( DownloadHelper.TABLE );
        String limit = null;

        switch( PageBank.uriMatcher.match(uri)){

            case PageBank.SINGLE_DOWNLOAD:
                builder.appendWhere( DownloadHelper.ID + "=" + uri.getPathSegments().get(1) );
                break;

            case PageBank.DOWNLOADS_LIMIT:
                limit = uri.getPathSegments().get(2);
            break;
        }

        Cursor cursor = builder.query( db, projection, selection, selectionArgs, null, null, sortOrder, limit );

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert( DownloadHelper.TABLE, null, values );

        if( id >= 0 ){

            Uri insertedId = ContentUris.withAppendedId(uri, id);

            context.getContentResolver().notifyChange( insertedId, null);
            return insertedId;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables( DownloadHelper.TABLE );

        Boolean all = false;
        switch ( PageBank.uriMatcher.match(uri))
        {
            case PageBank.SINGLE_DOWNLOAD:
                String temp = DownloadHelper.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
            case PageBank.ALL_DOWNLOADS:
                all = true;
                break;
        }

        if( all ){
            db.execSQL( "DELETE FROM " + DownloadHelper.TABLE );
            db.execSQL( "VACUUM");

            //notify from content resolver..
            context.getContentResolver().notifyChange(uri, null);

            return -1;
        }

        if( selection == null )
            selection = "1";

        int deleteCount = db.delete( DownloadHelper.TABLE, selection, selectionArgs );

        //notify from content resolver..
        context.getContentResolver().notifyChange( uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        switch ( PageBank.uriMatcher.match(uri))
        {
            case PageBank.SINGLE_DOWNLOAD:
                String temp = DownloadHelper.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
        }

        int updateCount = db.update(DownloadHelper.TABLE, values, selection, selectionArgs);

        //notify from content resolver..
        context.getContentResolver().notifyChange( uri, null);
        return updateCount;
    }
}
