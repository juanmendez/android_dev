package info.juanmendez.android.contentloader;

import android.content.ContentValues;

import java.util.List;

/**
 * Created by Juan on 7/5/2015.
 */
public interface QueryHandlerClient {
    public void onInsertComplete(List<ContentValues> values);
}
