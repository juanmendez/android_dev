package info.juanmendez.realminit.views;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import info.juanmendez.realminit.R;
import info.juanmendez.realminit.RealmApplication;
import info.juanmendez.realminit.adapters.song.SongAdapter;
import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.models.SongCom;
import info.juanmendez.realminit.models.SongStatus;
import info.juanmendez.realminit.services.SongService;
import io.realm.Realm;
import rx.Subscription;
import rx.subjects.PublishSubject;

/**
 * Created by Juan Mendez on 9/8/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
@EFragment(R.layout.fragment_songs)
@OptionsMenu(R.menu.songs_menu)
public class SongsFragment extends Fragment {
    @ViewById
    RealmRecyclerView songListView;

    @ViewById(R.id.right_pane)
    FrameLayout rightPane;

    @ViewById(R.id.fragmentToolbar)
    Toolbar toolBar;

    @Inject
    Realm realm;

    @Inject
    SongStatus songStatus;

    @Inject
    SongService songService;

    @Inject
    PublishSubject<SongCom> songSubject;

    SongAdapter adapter;
    Subscription songSubscription;
    String songTag = "songFormDialog";

    @AfterViews
    void afterViews(){
        setRetainInstance(true);
        RealmApplication.inject( this );
        configRecyclerView();
        toolBar.setTitle("List of songs");
        toolBar.inflateMenu( R.menu.songs_menu);



        /**
         * if song is selected, we need to allow the song to be updated.
         */
        songSubscription = songSubject.subscribe(songCom -> {

            if( songCom != null ){

                if( songCom.getStatus() == SongCom.READ ){
                    songStatus.setStatus( SongStatus.UPDATED, songCom.getSong().getId() );
                    createSongDialog();
                }else
                if( songCom.getStatus() == SongCom.DELETE ){
                    if( songCom.getConfirm() == SongCom.NONE ){
                        askToDelete( songCom );
                    }
                    else
                    if( songCom.getConfirm() == SongCom.YES ){
                        songService.deleteSong( songCom.getSong() );
                    }
                }
            }
        });
    }

    private void askToDelete(SongCom songCom) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setMessage( "Song: " + songCom.getSong().getTitle() )
                .setTitle( "Would you like to delete?");

        builder.setPositiveButton( R.string.ok, (dialogInterface, i) -> {
            songCom.setConfirm( SongCom.YES );
            songSubject.onNext( songCom );
        });

        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            songCom.setConfirm( SongCom.NO );
            songSubject.onNext( songCom );
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        songSubscription.unsubscribe();
    }

    public static Fragment create() {
        return SongsFragment_.builder().build();
    }

    public void configRecyclerView(){
        if( adapter == null ){
            adapter = new SongAdapter( getContext(), realm.where( Song.class ).findAll(), true, true, null);
            adapter.setSongSubject( songSubject );
        }
        songListView.setAdapter( adapter );
    }

    @Click( R.id.button_add_song )
    public void add_song_handler(){

        songStatus.setStatus( SongStatus.ADDED, -1 );
        createSongDialog();
    }

    private void createSongDialog(){

        FragmentManager fm = getFragmentManager();

        SongFormDialog songDialog = (SongFormDialog) fm.findFragmentByTag(songTag);

        if( songDialog == null ){
            songDialog = SongFormDialog.makeForm();
        }

        if( rightPane != null ){
            fm.beginTransaction().addToBackStack(songTag).add( R.id.right_pane, songDialog, songTag).commit();
        }
    }
}
