package info.juanmendez.realminit.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import info.juanmendez.realminit.R;
import info.juanmendez.realminit.RealmApplication;
import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.services.BandService;
import info.juanmendez.realminit.services.SongService;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * @author Juan Mendez
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu)
public class MainActivity extends AppCompatActivity {

    @Inject
    Realm realm;

    @Inject
    BandService bandService;

    @Inject
    SongService songService;

    @ViewById(R.id.toolBar)
    Toolbar toolBar;

    private static final String TAG = "MainActivity";

    RealmChangeListener changeListener;

    private void showLastSongCount(){
        long count = realm.where(Song.class).count();
        setTitle( "Number of songs " + count );
    }

    @AfterViews
    protected void afterViews(){
        RealmApplication.inject( this );
        setSupportActionBar( toolBar );
        changeListener = element->showLastSongCount();
    }

    @Override
    protected void onResume() {
        super.onResume();

        realm.addChangeListener( changeListener );
        showLastSongCount(); //start showing number of songs..

        bandService.getObserver().subscribe(band -> {
            Log.i( TAG, band.toString() );
        },throwable->{
            Toast.makeText( this, throwable.getMessage(), Toast.LENGTH_LONG ).show();
        } );


        songService.getObserver().subscribe(song->{
            this.removeSongFormDialog();
        }, throwable->{
            Toast.makeText( this, throwable.getMessage(), Toast.LENGTH_LONG ).show();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        realm.removeAllChangeListeners();
    }

    @OptionsItem( R.id.action_song)
    void addSongHandler(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment songsFragment = fm.findFragmentByTag("songsFragment");

        if( songsFragment == null ){
            songsFragment = SongsFragment.create();
        }

        fm.beginTransaction().add( R.id.mainFramelayout, songsFragment, "songsFragment").addToBackStack("songsFragment").commit();
    }

    @OptionsItem( R.id.action_band)
    void onNewBandRequest(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment bandsFragment = fm.findFragmentByTag( "bandsFragment");

        if( bandsFragment == null ){
            bandsFragment = BandsFragment.create();
        }

        fm.beginTransaction().add( R.id.mainFramelayout, bandsFragment, "bandsFragment").addToBackStack("bandsFragment").commit();
    }

    /**
     * This activity could have used an interface, or an event a bus communication between
     * its fragment, but for simplicity and demonstration it is a straight method call.
     */
    public void removeSongFormDialog(){

        FragmentManager fm = getSupportFragmentManager();
        Fragment songFragment = fm.findFragmentByTag("songFormDialog");

        if( songFragment != null )
            fm.beginTransaction().remove(  songFragment ).commit();
    }
}
