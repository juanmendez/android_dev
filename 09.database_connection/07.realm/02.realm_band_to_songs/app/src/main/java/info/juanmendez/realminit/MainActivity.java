package info.juanmendez.realminit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.services.BandService;
import info.juanmendez.realminit.services.SongService;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * @author Juan Mendez
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Inject
    Realm realm;

    @Inject
    BandService bandService;

    @Inject
    SongService songService;

    private static final String TAG = "MainActivity";

    RealmChangeListener changeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            showLastSongCount();
        }
    };

    private void showLastSongCount(){
        long count = realm.where(Song.class).count();
        setTitle( "Number of songs " + count );
    }

    @Override
    protected void onResume() {
        super.onResume();
        RealmApplication.inject( this );

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

    @Click(R.id.addSongButton)
    public void onNewSongRequest(){

        FragmentManager fm = getSupportFragmentManager();
        Fragment songDialog = fm.findFragmentByTag("songFormDialog");

        if( songDialog == null ){
            songDialog = SongFormDialog.makeForm();
        }

        fm.beginTransaction().add( android.R.id.content, songDialog, "songFormDialog").addToBackStack("songFormDialog").commit();
    }


    @Click( R.id.addBandButton )
    public void onNewBandRequest(){
        FragmentManager fm = getSupportFragmentManager();
        BandFormDialog bandFormDialog = new BandFormDialog();
        bandFormDialog.show( fm, "band_form" );
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
