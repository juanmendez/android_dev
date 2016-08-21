package info.juanmendez.realminit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import info.juanmendez.realminit.models.Song;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        realm = Realm.getDefaultInstance();

        //this way works,, but the next way is optimal
        /*try {
            Song song = realm.createObject( Song.class );
            song.setId(0);
            song.setTitle("Welcome to the Jungle");
            song.setVideo_url( "https://www.youtube.com/watch?v=o1tj2zJ2Wvg" );
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        realm.executeTransaction(theRealm -> {
            Song song = theRealm.createObject( Song.class );
            song.setId(0);
            song.setTitle("Welcome to the Jungle");
            song.setVideo_url( "https://www.youtube.com/watch?v=o1tj2zJ2Wvg" );
        });

        /**
         * the code above won't work on device rotation.. makes total sense..
         * Unable to resume activity {info.juanmendez.realminit/info.juanmendez.realminit.MainActivity}:
         * io.realm.exceptions.RealmPrimaryKeyConstraintException: Value already exists: 0
         */

        RealmResults<Song>songs = realm.where(Song.class).findAll();

        for( Song song: songs ){
            Log.d( "Realm", song.getTitle() );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        realm.close();
    }
}
