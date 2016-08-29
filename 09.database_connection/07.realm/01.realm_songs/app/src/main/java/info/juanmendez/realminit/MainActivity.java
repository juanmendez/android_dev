package info.juanmendez.realminit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import info.juanmendez.realminit.models.Song;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * @author Juan Mendez
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private Realm realm;
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
        realm = Realm.getDefaultInstance();
        realm.addChangeListener( changeListener );
        showLastSongCount(); //start showing number of songs..
    }

    @Override
    protected void onPause() {
        super.onPause();
        realm.close();
        realm.removeAllChangeListeners();
    }

    @Click(R.id.addSongButton)
    public void onNewSongRequest(){

        FragmentManager fm = getSupportFragmentManager();
        Fragment songFragment = fm.findFragmentByTag("songFormFragment");

        if( songFragment == null ){
            songFragment = SongFormFragment.makeForm();
        }

        fm.beginTransaction().add( android.R.id.content, songFragment, "songFormFragment").commit();
    }

    /**
     * This activity could have used an interface, or an event a bus communication between
     * its fragment, but for simplicity and demonstration it is a straight method call.
     */
    public void kickMeOut(){

        FragmentManager fm = getSupportFragmentManager();
        Fragment songFragment = fm.findFragmentByTag("songFormFragment");

        if( songFragment != null )
            fm.beginTransaction().remove(  songFragment ).commit();
    }
}
