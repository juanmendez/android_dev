package info.juanmendez.android.intentservice.service.provider;

import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;

import info.juanmendez.android.intentservice.helper.MagazineParser;
import info.juanmendez.android.intentservice.model.Magazine;

/**
 * Created by Juan on 8/2/2015.
 */
public class MagazineLoader extends AsyncTaskLoader<ArrayList<Magazine>> {

    ArrayList<Magazine> magazines = new ArrayList<Magazine>();
    ContentResolver resolver;

    public MagazineLoader( Context context ){
        super( context );

        resolver = context.getContentResolver();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if( !magazines.isEmpty())
        {
            deliverResult(magazines);
        }
        else{
            forceLoad();
        }
    }

    @Override
    public ArrayList<Magazine> loadInBackground() {

        magazines.clear();
        Magazine magazine;
        Uri uri = Uri.parse("content://" + MagazineProvider.AUTHORITY + "/magazines/");
        Cursor query = resolver.query(uri,
                new String[]{SQLMagazine.ID, SQLMagazine.ISSUE, SQLMagazine.DATETIME, SQLMagazine.LOCATION, SQLMagazine.FILE_LOCATION, SQLMagazine.TITLE },
                null,
                null,
                SQLMagazine.ISSUE + " desc");

        while( query.moveToNext() )
        {
            magazines.add(MagazineParser.fromCursor( query ));
        }

        return magazines;
    }


    @Override
    public void deliverResult( ArrayList<Magazine> magazines ){

        if( this.magazines == null )
            this.magazines = magazines;

        if( isStarted() )
        {
            super.deliverResult( magazines );
        }

    }

    public ArrayList<Magazine> getMagazines() {
        return magazines;
    }
}
