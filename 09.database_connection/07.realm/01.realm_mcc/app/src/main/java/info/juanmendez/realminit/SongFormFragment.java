package info.juanmendez.realminit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import info.juanmendez.realminit.models.Song;

/**
 * Created by musta on 8/22/2016.
 */
@EFragment(R.layout.song_form)
public class SongFormFragment extends Fragment {
    private MainActivity mainActivity;
    @ViewById
    EditText titleInput;

    @ViewById
    EditText urlInput;

    @ViewById
    EditText yearInput;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
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

        mainActivity.onSongSubmitted( song );
    }
}
