package info.juanmendez.android.recyclerview.ui.listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import info.juanmendez.android.recyclerview.App;
import info.juanmendez.android.recyclerview.R;
import info.juanmendez.android.recyclerview.model.Country;
import info.juanmendez.android.recyclerview.rx.UIObservable;
import info.juanmendez.android.recyclerview.ui.detail.WikiActivity;
import info.juanmendez.android.recyclerview.ui.detail.WikiFragment;
import info.juanmendez.android.recyclerview.ui.listing.recyclerview.CountryAdapter;
import rx.Observer;
import rx.Subscription;

public class RecyclerActivity extends AppCompatActivity {

    CountriesFragment countriesFragment;
    WikiFragment wikiFragment;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        countriesFragment = (CountriesFragment) getSupportFragmentManager().findFragmentById(R.id.countriesFragment);
        wikiFragment = (WikiFragment) getSupportFragmentManager().findFragmentById(R.id.wikiFragment);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if( wikiFragment != null ){

            subscription = getObservable().subscribe(country -> {

                if( !getResources().getBoolean(R.bool.dual_fragments) )
                {
                    Intent i = new Intent( this, WikiActivity.class );
                    i.putExtra("url", country.getLink());
                    startActivity(i);
                }
            });
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        if( wikiFragment != null ){

            getObservable().unsubscribe( subscription );
        }
    }

    private UIObservable getObservable(){

        return ((App) getApplication()).getObservable();
    }
}
