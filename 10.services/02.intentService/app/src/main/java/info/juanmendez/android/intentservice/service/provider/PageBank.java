package info.juanmendez.android.intentservice.service.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Juan on 7/20/2015.
 */
public class PageBank extends ContentProvider {

    static final int ALL_PAGES = 1;
    static final int SINGLE_PAGE = 2;

    static final int ALL_DOWNLOADS = 3;
    static final int SINGLE_DOWNLOAD = 4;
    static final int DOWNLOADS_LIMIT = 5;

    private PageCrud pageCrud;
    private DownloadCrud downloadCrud;
    public static final String AUTHORITY = "info.juanmendez.android.intentservice.service.provider.PageBank";

    public static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        uriMatcher.addURI(AUTHORITY, "pages", ALL_PAGES);
        uriMatcher.addURI(AUTHORITY, "pages/#", SINGLE_PAGE);

        uriMatcher.addURI(AUTHORITY, "downloads", ALL_DOWNLOADS);
        uriMatcher.addURI(AUTHORITY, "downloads/#", SINGLE_DOWNLOAD);
        uriMatcher.addURI(AUTHORITY, "downloads/limit/#", DOWNLOADS_LIMIT);
    }

    @Override
    public boolean onCreate() {
        pageCrud = new PageCrud( getContext() );
        downloadCrud = new DownloadCrud( getContext() );
        return true;
    }

    @Override
    public String getType(Uri uri) {

        switch( uriMatcher.match(uri)){
            case ALL_PAGES:
                return "vnd.android.cursor.dir/vnd.juanmendez.pages";
            case SINGLE_PAGE:
                return "vnd.android.cursor.item/vnd.juanmendez.pages";
            case ALL_DOWNLOADS:
                return "vnd.android.cursor.dir/vnd.juanmendez.downloads";
            case SINGLE_DOWNLOAD:
            case DOWNLOADS_LIMIT:
                return "vnd.android.cursor.item/vnd.juanmendez.downloads";
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

                case "downloads":
                    return downloadCrud.query( uri, projection, selection, selectionArgs, sortOrder );
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

                case "downloads":
                    return downloadCrud.insert( uri, values );
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

                case "downloads":
                    return downloadCrud.delete(uri, selection, selectionArgs);
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

                case "downloads":
                    return downloadCrud.update( uri, values, selection, selectionArgs );
            }
        }

        return 0;
    }
}
