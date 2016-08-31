package info.juanmendez.realminit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import info.juanmendez.realminit.models.Band;
import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.services.SongService;
import io.realm.Realm;

@EFragment(R.layout.song_form)
public class SongFormDialog extends DialogFragment {

    @ViewById
    EditText titleInput;

    @ViewById
    EditText urlInput;

    @ViewById
    EditText yearInput;

    @ViewById
    RecyclerView bandListView;

    @Inject
    SongService songService;

    @Inject
    Realm realm;

    BandAdapter adapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        RealmApplication.inject( this );
        configRecyclerView();
    }

    public static SongFormDialog makeForm(){
        SongFormDialog fragmment = SongFormDialog_.builder().build();
        return fragmment;
    }

    @Click(R.id.submit_button)
    public void onSubmit(){
        Song song = new Song();
        song.setTitle( titleInput.getText().toString() );
        song.setVideo_url( urlInput.getText().toString() );
        song.setYear( Integer.parseInt( yearInput.getText().toString()) );

        if( adapter.getBandSelected()!=null ){
            song.setBand( adapter.getBandSelected());
        }

        songService.add( song );
    }

    public void configRecyclerView(){

        bandListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getContext() );
        bandListView.setLayoutManager( layoutManager );

        if( adapter == null ){
            adapter = new BandAdapter( realm.where( Band.class ).findAll());
        }
        bandListView.setAdapter( adapter );
    }
}
