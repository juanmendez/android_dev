package info.juanmendez.android.intentservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import dagger.ObjectGraph;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.module.ActivityModule;
import info.juanmendez.android.intentservice.ui.magazine.IMagazineView;
import info.juanmendez.android.intentservice.ui.magazine.MagazinePresenter;

public class MagazineActivity extends AppCompatActivity implements IMagazineView {

    ViewPager viewPager;
    ObjectGraph graph;

    MagazinePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        viewPager = (ViewPager) findViewById(R.id.pager);

        MagazineApp app = (MagazineApp)getApplication();
        graph = app.getGraph().plus( new ActivityModule(this));
        graph.inject(this);

        presenter = new MagazinePresenter(this);
    }

    public void inject( Object object ){
        graph.inject(object);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause(){
        super.onPause();
        presenter.pause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if( id == R.id.action_settings ){
            Intent i = new Intent( this, SettingsHolderActivity.class );
            startActivity( i );
        }

        return super.onOptionsItemSelected(item);
    }
}