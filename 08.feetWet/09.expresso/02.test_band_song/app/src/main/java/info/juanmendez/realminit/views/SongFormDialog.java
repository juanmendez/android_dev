package info.juanmendez.realminit.views;

import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import info.juanmendez.realminit.R;
import info.juanmendez.realminit.RealmApplication;
import info.juanmendez.realminit.adapters.band.BandAdapter;
import info.juanmendez.realminit.models.Band;
import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.models.SongCom;
import info.juanmendez.realminit.services.SongService;
import io.realm.Realm;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

@EFragment(R.layout.song_form)
public class SongFormDialog extends DialogFragment {

    @Inject
    SongService songService;

    @Inject
    Realm realm;

    @Inject
    BehaviorSubject<SongCom> songSubject;

    @ViewById
    EditText titleInput;

    @ViewById
    EditText urlInput;

    @ViewById
    EditText yearInput;

    @ViewById
    RealmRecyclerView bandListView;

    BandAdapter adapter;
    Song song;

    Subscription songSubscription;

    @AfterViews
    protected void onAfterViews(){
        RealmApplication.inject( this );
        configRecyclerView();
        subscribeToSongSubject();
    }

    @Override
    public void onPause(){

        super.onPause();
        songSubscription.unsubscribe();
    }

    void subscribeToSongSubject(){
        /**
         * if song is selected, we need to allow the song to be updated.
         */
        songSubscription = songSubject.subscribe(songCom -> {

            if( songCom != null ){

                if( songCom.getStatus() == SongCom.READ ){

                    song = songCom.getSong();

                    titleInput.setText( song.getTitle() );
                    urlInput.setText( song.getVideo_url() );
                    yearInput.setText( Integer.toString(song.getYear()), TextView.BufferType.EDITABLE );

                    if( song.getBand() != null ){
                        adapter.setBandSelected( song.getBand() );
                    }
                }
            }
        });
    }

    @Click(R.id.submit_button)
    public void onSubmit(){
        Song mockSong = new Song();
        mockSong.setTitle( titleInput.getText().toString() );
        mockSong.setVideo_url( urlInput.getText().toString() );
        mockSong.setYear( Integer.parseInt( yearInput.getText().toString()) );

        if( adapter.getBandSelected()!=null ){
            mockSong.setBand( adapter.getBandSelected());
        }

        if( song.getId() >= 0 ){
            mockSong.setId(  song.getId() );
        }

        songService.addOrUpdate( mockSong );
    }

    @Click( R.id.delete_button )
    protected void onDeleteSong(){
        songService.deleteSong( song );
    }

    public void configRecyclerView(){

        if( adapter == null ){
            adapter = new BandAdapter( getContext(), realm.where( Band.class ).findAll(), true, true, null);
        }
        bandListView.setAdapter( adapter );
    }

    public static SongFormDialog makeForm(){
        SongFormDialog fragmment = SongFormDialog_.builder().build();
        return fragmment;
    }
}
