package info.juanmendez.android.recyclerview.ui.listing;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import info.juanmendez.android.recyclerview.App;
import info.juanmendez.android.recyclerview.R;
import info.juanmendez.android.recyclerview.rx.UIObservable;
import info.juanmendez.android.recyclerview.ui.detail.WikiFragment;
import rx.Subscription;

public class RecyclerActivity extends AppCompatActivity {

    CountriesFragment countriesFragment;
    WikiFragment wikiFragment;
    Subscription subscription;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(v -> {

        });
        setSupportActionBar(toolbar);

        countriesFragment = (CountriesFragment) getSupportFragmentManager().findFragmentById(R.id.countriesFragment);
        wikiFragment = (WikiFragment) getSupportFragmentManager().findFragmentById(R.id.wikiFragment);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if( drawerLayout != null )
        {
            toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close );
            drawerLayout.setDrawerListener(toggle);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        subscription = getObservable().subscribe(country -> {

            if( drawerLayout != null ) //close drawer once an item is selected
            {
                drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();

        if( subscription != null ){
            getObservable().unsubscribe( subscription );
        }
    }

    private UIObservable getObservable(){

        return ((App) getApplication()).getObservable();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if ( toggle != null && toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
