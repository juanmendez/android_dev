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
import info.juanmendez.android.intentservice.service.provider.table.SQLMagazine;

/**
 * Created by Juan on 7/20/2015.
 */
public class MagazineCrud implements CrudProvider {

    SQLMagazine helper;
    Context context;

    public MagazineCrud(Context context){
        this.context = context;
        this.helper = new SQLMagazine( context );
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables( SQLMagazine.TABLE );
        String limit = null;

        switch( MagazineProvider.uriMatcher.match(uri)){

            case MagazineProvider.SINGLE_MAGAZINE:
                builder.appendWhere( SQLMagazine.ID + "=" + uri.getPathSegments().get(1) );
                break;

            case MagazineProvider.MAGAZINE_LIMIT:
                limit = uri.getPathSegments().get(2);
            break;
        }

        Cursor cursor = builder.query( db, projection, selection, selectionArgs, null, null, sortOrder, limit );

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
SQLiteDatabase db = helper.getWritableDatabase();

try{
    long id = db.insertOrThrow(SQLMagazine.TABLE, null, values);

    if( id >= 0 ){

        Uri insertedId = ContentUris.withAppendedId(uri, id);

        context.getContentResolver().notifyChange( insertedId, null);
        return insertedId;
    }
}
catch(SQLiteException e ){
    e.printStackTrace();
}

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables( SQLMagazine.TABLE );

        Boolean all = false;
        switch ( MagazineProvider.uriMatcher.match(uri))
        {
            case MagazineProvider.SINGLE_MAGAZINE:
                String temp = SQLMagazine.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
            case MagazineProvider.ALL_MAGAZINES:
                all = true;
                break;
        }

        if( all ){
            db.execSQL( "DELETE FROM " + SQLMagazine.TABLE );
            db.execSQL( "VACUUM");

            //notify from content resolver..
            context.getContentResolver().notifyChange(uri, null);

            return -1;
        }

        if( selection == null )
            selection = "1";

        int deleteCount = db.delete( SQLMagazine.TABLE, selection, selectionArgs );

        //notify from content resolver..
        context.getContentResolver().notifyChange( uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        switch ( MagazineProvider.uriMatcher.match(uri))
        {
            case MagazineProvider.SINGLE_MAGAZINE:
                String temp = SQLMagazine.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");

                int updateCount = db.update(SQLMagazine.TABLE, values, selection, selectionArgs );

                //notify from content resolver..
                context.getContentResolver().notifyChange( uri, null);
                return updateCount;
        }

        return 0;
    }
}
