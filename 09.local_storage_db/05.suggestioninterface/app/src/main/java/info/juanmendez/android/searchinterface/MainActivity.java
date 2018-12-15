package info.juanmendez.android.searchinterface;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver resolver = getContentResolver();

        Uri content_uri = Uri.parse( "content://" + MyCustomSuggestionProvider.AUTHORITY );

        ContentValues row = new ContentValues();
        row.put(OpenHelper.NAME, "Guatemala");
        resolver.insert(content_uri, row);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "El Salvador");
        resolver.insert(content_uri, row);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Honduras");
        resolver.insert(content_uri, row);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Nicaragua");
        resolver.insert(content_uri, row);

        row = new ContentValues();
        row.put(OpenHelper.NAME, "Costa Rica");
        resolver.insert(content_uri, row);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dialog) {

            onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
