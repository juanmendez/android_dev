package info.juanmendez.android.intentservice.service.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import info.juanmendez.android.intentservice.BuildConfig;

/**
 * Created by Juan on 7/20/2015.
 */
public class MagazineProvider extends ContentProvider {

    public static final int ALL_PAGES = 1;
    public static final int SINGLE_PAGE = 2;

    public static final int ALL_MAGAZINES = 3;
    public static final int SINGLE_MAGAZINE = 4;
    public static final int MAGAZINE_LIMIT = 5;

    private PageCrud pageCrud;
    private MagazineCrud magazineCrud;
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider";

    public static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        uriMatcher.addURI(AUTHORITY, "pages", ALL_PAGES);
        uriMatcher.addURI(AUTHORITY, "pages/#", SINGLE_PAGE);

        uriMatcher.addURI(AUTHORITY, "magazines", ALL_MAGAZINES);
        uriMatcher.addURI(AUTHORITY, "magazines/#", SINGLE_MAGAZINE);
        uriMatcher.addURI(AUTHORITY, "magazines/limit/#", MAGAZINE_LIMIT);
    }

    @Override
    public boolean onCreate() {

        pageCrud = new PageCrud( getContext() );
        magazineCrud = new MagazineCrud( getContext() );
        return true;
    }

    @Override
    public String getType(Uri uri) {

        switch( uriMatcher.match(uri)){
            case ALL_PAGES:
                return "vnd.android.cursor.dir/vnd.juanmendez.pages";
            case SINGLE_PAGE:
                return "vnd.android.cursor.item/vnd.juanmendez.pages";
            case ALL_MAGAZINES:
                return "vnd.android.cursor.dir/vnd.juanmendez.magazines";
            case SINGLE_MAGAZINE:
            case MAGAZINE_LIMIT:
                return "vnd.android.cursor.item/vnd.juanmendez.magazines";
            default:
                throw new IllegalArgumentException( "Unsupported URI " + uri );
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if( uri.getPathSegments().size() > 0 )
        {
            switch ( uri.getPathSegments().get(0)){
                case "pages":
                    return pageCrud.query( uri, projection, selection, selectionArgs, sortOrder );

                case "magazines":
                    return magazineCrud.query( uri, projection, selection, selectionArgs, sortOrder );
            }
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if( uri.getPathSegments().size() > 0 )
        {
            switch ( uri.getPathSegments().get(0)){
                case "pages":
                    return pageCrud.insert( uri, values );

                case "magazines":
                    return magazineCrud.insert( uri, values );
            }
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        if( uri.getPathSegments().size() > 0 )
        {
            switch ( uri.getPathSegments().get(0)){
                case "pages":
                    return pageCrud.delete(uri, selection, selectionArgs);

                case "magazines":
                    return magazineCrud.delete(uri, selection, selectionArgs);
            }
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if( uri.getPathSegments().size() > 0 )
        {
            switch ( uri.getPathSegments().get(0)){
                case "pages":
                    return pageCrud.update( uri, values, selection, selectionArgs );

                case "magazines":
                    return magazineCrud.update( uri, values, selection, selectionArgs );
            }
        }

        return 0;
    }
}
