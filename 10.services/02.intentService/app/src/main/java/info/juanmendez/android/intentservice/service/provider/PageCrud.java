package info.juanmendez.android.intentservice.service.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Juan on 7/20/2015.
 */
public class PageCrud implements CrudProvider
{
    PageHelper helper;
    Context context;

    public PageCrud( Context context ){
        this.context = context;
        this.helper = new PageHelper( context );
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables( PageHelper.TABLE );

        switch( PageBank.uriMatcher.match(uri)){

            case PageBank.SINGLE_PAGE:
                builder.appendWhere( PageHelper.ID + "=" + uri.getPathSegments().get(1) );
                break;
        }

        Cursor cursor = builder.query( db, projection, selection, selectionArgs, null, null, sortOrder );

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert( PageHelper.TABLE, null, values );

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
        builder.setTables( PageHelper.TABLE );

        Boolean all = false;

        switch ( PageBank.uriMatcher.match(uri))
        {
            case PageBank.SINGLE_PAGE:
                String temp = PageHelper.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
            case PageBank.ALL_PAGES:
                all = true;
                break;
        }

        if( all ){
            db.execSQL( "truncate " + PageHelper.TABLE );
            return -1;
        }


        if( selection == null )
            selection = "1";

        int deleteCount = db.delete( PageHelper.TABLE, selection, selectionArgs );

        //notify from content resolver..
        context.getContentResolver().notifyChange( uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        switch ( PageBank.uriMatcher.match(uri))
        {
            case PageBank.SINGLE_PAGE:
                String temp = PageHelper.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
        }

        int updateCount = db.update(PageHelper.TABLE, values, selection, selectionArgs);

        //notify from content resolver..
        context.getContentResolver().notifyChange( uri, null);
        return updateCount;
    }
}
