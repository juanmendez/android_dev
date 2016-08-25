package info.juanmendez.realminit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import info.juanmendez.realminit.models.Song;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Juan Mendez
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private static final String TAG = "MainActivity";

    @Override
    protected void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onPause() {
        super.onPause();
        realm.close();
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
     * This activity could have used an interface, and event a bus communication between
     * its fragment, but for simplicity and demonstration it is a straight method call.
     * @param song
     */
    public void onSongSubmitted( Song song ){

        FragmentManager fm = getSupportFragmentManager();
        Fragment songFragment = fm.findFragmentByTag("songFormFragment");
        fm.beginTransaction().remove(  songFragment ).commit();

        /**
         * Asynchronous Transaction
         */

        realm.executeTransactionAsync( thisRealm->{
            /**
             * you can create an instance of an object first and add it later using realm.copyToRealm()
             */

            int nextID = (thisRealm.where(Song.class).max("id")).intValue() + 1;
            song.setId( nextID );
            Song s = thisRealm.copyToRealm( song );
        },
        ()->{
            Log.i( TAG, "song has been created");
        },
        error->{
            Log.e( TAG, "an error while saving " + error.getMessage() );
        });

    }
}
