package info.juanmendez.android.searchinterface;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Juan on 7/1/2015.
 */
public class SearchActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach_activity);

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = (TextView) findViewById(R.id.text);

        Intent intent = getIntent();
        if( Intent.ACTION_SEARCH.equals( intent.getAction() )){
            String query = intent.getStringExtra(SearchManager.QUERY);
            textView.setText( "search for " + query );
            textView.setTextColor(Color.BLUE);
        }
        else
        if( Intent.ACTION_VIEW.equals( intent.getAction() )){
            Uri uri = intent.getData();
            Cursor cursor = managedQuery(uri, null, null, null, null);

            while( cursor.moveToNext() )
            {
                Toast.makeText(this, "You searched for " + cursor.getString(cursor.getColumnIndexOrThrow(OpenHelper.NAME)), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {textView.setText( "wrong intention" + intent.getAction() );
            textView.setTextColor(Color.RED);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if( id == android.R.id.home )
        {
            // app icon in action bar clicked; go home
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
