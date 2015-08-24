package info.juanmendez.android.intentservice.service.provider.crud;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


public interface CrudProvider {

    Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder );

    Uri insert(Uri uri, ContentValues values);

    int delete(Uri uri, String selection, String[] selectionArgs);

    int update(Uri uri, ContentValues values, String selection, String[] selectionArgs);
}
