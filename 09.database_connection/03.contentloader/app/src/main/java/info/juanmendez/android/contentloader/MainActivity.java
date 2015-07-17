package info.juanmendez.android.contentloader;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>, QueryHandlerClient {

    ListView list;
    SimpleCursorAdapter adapter;
    QueryHandler queryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        adapter = new SimpleCursorAdapter( this, android.R.layout.simple_list_item_2, null, new String[]{ OpenHelper.NAME}, new int[]{android.R.id.text2}, 0  );
        list.setAdapter( adapter );

        queryHandler = new QueryHandler( getContentResolver(), this );
        insertFirstItems();
    }

    public void insertFirstItems()
    {
        List<ContentValues> values = new ArrayList<ContentValues>();
        ContentValues contentValues = new ContentValues();
        contentValues.put( OpenHelper.NAME, "Guatemala" );
        values.add( contentValues );

        contentValues = new ContentValues();
        contentValues.put( OpenHelper.NAME, "El Salvador" );
        values.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put( OpenHelper.NAME, "Honduras" );
        values.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put( OpenHelper.NAME, "Nicaragua" );
        values.add(contentValues);

        contentValues = new ContentValues();
        contentValues.put( OpenHelper.NAME, "Costa Rica" );
        values.add(contentValues);

        queryHandler.startInsert( values, CountryProvider.URILOC );
    }

    public void onInsertComplete(List<ContentValues> values) {
        getSupportLoaderManager().initLoader(0, null, this );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader cursorLoader = new CursorLoader( this,
                Uri.parse("content://" + CountryProvider.AUTHORITY + "/elements"),
                new String[]{OpenHelper.ID, OpenHelper.NAME},
                "",
                null,
                "" );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor( data );
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.print( "load reset!" + loader.toString() );
    }
}
