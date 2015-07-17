package info.juanmendez.android.contentloader;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Message;

import java.util.List;

public class QueryHandler extends AsyncQueryHandler {

    List<ContentValues> insertValues;
    QueryHandlerClient client;

    public QueryHandler(ContentResolver cr, QueryHandlerClient client ) {
        super(cr);
        this.client = client;
    }

    public void startInsert( List<ContentValues> items, Uri uri ){

        insertValues = items;
        int i = 0;

        for( ContentValues contentValues: insertValues ){
            startInsert( ++i, null, uri, contentValues );
        }
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        super.onInsertComplete(token, cookie, uri);

        if( insertValues != null && token == insertValues.size() )
        {
            client.onInsertComplete( insertValues);
            insertValues = null;
        }
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        super.onUpdateComplete(token, cookie, result);
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        super.onDeleteComplete(token, cookie, result);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}