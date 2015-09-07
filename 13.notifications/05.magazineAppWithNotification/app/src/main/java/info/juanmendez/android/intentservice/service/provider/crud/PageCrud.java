package info.juanmendez.android.intentservice.service.provider.crud;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import info.juanmendez.android.intentservice.service.provider.MagazineProvider;
import info.juanmendez.android.intentservice.service.provider.table.SQLPage;
import info.juanmendez.android.intentservice.service.provider.table.SqlHelper;

/**
 * Created by Juan on 7/20/2015.
 */
public class PageCrud implements CrudProvider
{
    SqlHelper helper;
    Context context;

    public PageCrud( Context context, SqlHelper helper ){
        this.context = context;
        this.helper = helper;
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables( SQLPage.TABLE );

        switch( MagazineProvider.uriMatcher.match(uri)){

            case MagazineProvider.SINGLE_PAGE:
                builder.appendWhere( SQLPage.ID + "=" + uri.getPathSegments().get(1) );
                break;
        }

        Cursor cursor = builder.query( db, projection, selection, selectionArgs, null, null, sortOrder );
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();

        try{
            long id = db.insertOrThrow( SQLPage.TABLE, null, values );

            if( id >= 0 ){

                Uri insertedId = ContentUris.withAppendedId(uri, id);

                context.getContentResolver().notifyChange( insertedId, null);
                return insertedId;
            }
        }
        catch (SQLiteException e ){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables( SQLPage.TABLE );

        Boolean all = false;

        switch ( MagazineProvider.uriMatcher.match(uri))
        {
            case MagazineProvider.SINGLE_PAGE:
                String temp = SQLPage.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
            case MagazineProvider.ALL_PAGES:
                all = true;
                break;
        }

        if( all ){
            db.execSQL( "truncate " + SQLPage.TABLE );
            return -1;
        }


        if( selection == null )
            selection = "1";

        int deleteCount = db.delete( SQLPage.TABLE, selection, selectionArgs );

        //notify from content resolver..
        context.getContentResolver().notifyChange( uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        switch ( MagazineProvider.uriMatcher.match(uri))
        {
            case MagazineProvider.SINGLE_PAGE:
                String temp = SQLPage.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
        }

        int updateCount = db.update(SQLPage.TABLE, values, selection, selectionArgs);

        //notify from content resolver..
        context.getContentResolver().notifyChange( uri, null);
        return updateCount;
    }
}
