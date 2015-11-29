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
import info.juanmendez.android.intentservice.service.provider.table.SqlHelper;

/**
 * Created by Juan on 7/20/2015.
 *
 * 8.4.2015
 * I realized that while keeping the SQLiteOpenHelper within the content provider
 * there was a case the db found itself trying to connect to two different threads.
 * I commented out db commands dealing with transactions. But surely is best to keep
 * the SQLiteOpenHelper within a contentProvider.
 */
public class MagazineCrud implements CrudProvider {

    SqlHelper helper;
    Context context;

    public MagazineCrud(Context context, SqlHelper helper){
        this.context = context;
        this.helper = helper;
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            //db.beginTransaction();
            SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
            builder.setTables(SQLMagazine.TABLE);
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

            //db.setTransactionSuccessful();
            //db.endTransaction();

            return cursor;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        //db.beginTransaction();
        try{
            long id = db.insertOrThrow(SQLMagazine.TABLE, null, values);
            //db.setTransactionSuccessful();
            //db.endTransaction();

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

        switch ( MagazineProvider.uriMatcher.match(uri))
        {
            case MagazineProvider.SINGLE_MAGAZINE:
                String temp = SQLMagazine.ID + "=" + uri.getPathSegments().get(1);
                selection = temp + ( !TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
            case MagazineProvider.ALL_MAGAZINES:
                selection = "1";
                break;
        }

        //db.beginTransaction();
        int deleteCount = db.delete( SQLMagazine.TABLE, selection, selectionArgs );
        //db.setTransactionSuccessful();
        //db.endTransaction();

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

                //db.beginTransaction();
                int updateCount = db.update(SQLMagazine.TABLE, values, selection, selectionArgs );
                //db.setTransactionSuccessful();
                //db.endTransaction();

                //notify from content resolver..
                context.getContentResolver().notifyChange( uri, null);
                return updateCount;
        }

        return 0;
    }
}
