package info.juanmendez.realminit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.realminit.models.Song;
import io.realm.Realm;

@EFragment(R.layout.song_form)
public class SongFormFragment extends Fragment {
    private MainActivity mainActivity;
    @ViewById
    EditText titleInput;

    @ViewById
    EditText urlInput;

    @ViewById
    EditText yearInput;

    private Realm realm;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        realm = Realm.getDefaultInstance();
    }

    public static SongFormFragment makeForm(){
        SongFormFragment fragmment = SongFormFragment_.builder().build();
        return fragmment;
    }

    @Click(R.id.submit_button)
    public void onSubmit(){
        Song song = new Song();
        song.setTitle( titleInput.getText().toString() );
        song.setVideo_url( urlInput.getText().toString() );
        song.setYear( Integer.parseInt( yearInput.getText().toString()) );

        realm.executeTransactionAsync( thisRealm->{

            int nextID = 0;
            Number lastId = thisRealm.where(Song.class).max("id");

            if( lastId != null ){
                nextID = lastId.intValue() + 1;
            }

            /*you can create an instance of an object first and add it later using realm.copyToRealm()*/
            song.setId( nextID );
            Song s = thisRealm.copyToRealm( song );
        },
        ()->{
            mainActivity.kickMeOut();
        },
        error->{
            Toast.makeText( mainActivity, "an error while saving " + error.getMessage(), Toast.LENGTH_LONG ).show();
        });
    }
}