package info.juanmendez.realminit.adapters.song;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import info.juanmendez.realminit.models.Song;
import info.juanmendez.realminit.models.SongCom;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by Juan Mendez on 9/8/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
public class SongAdapter extends RealmBasedRecyclerViewAdapter<Song, SongViewHolder> {
    
    SongCom songSelected = new SongCom();
    BehaviorSubject<SongCom> songSubject;

    public SongAdapter(Context context, RealmResults<Song> realmResults, boolean automaticUpdate, boolean animateResults, String animateExtraColumnName) {
        super(context, realmResults, automaticUpdate, animateResults, animateExtraColumnName);
    }

    @Override
    public SongViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate( android.R.layout.simple_list_item_1, viewGroup, false);

        return new SongViewHolder( v, this );
    }

    @Override
    public void onBindRealmViewHolder(SongViewHolder holder, int position) {
        final Song song = realmResults.get(position);
        holder.setSong( song );
        holder.setSelected( song.equals( songSelected.getSong() ) );
    }

    public void setSongSelected(Song _song) {

        if(  this.songSelected != null && songSelected.getSong() != null ){

            if( this.songSelected.isSameSong( _song ) )
                return;

            notifyItemChanged( realmResults.indexOf(songSelected.getSong()) );
        }

        this.songSelected = new SongCom();
        this.songSelected.setSong(_song);
        this.songSelected.setStatus( SongCom.READ );

        songSubject.onNext( this.songSelected );
    }

    public void setSongDeleted(Song songDeleted) {
        this.songSelected = new SongCom();
        this.songSelected.setSong( songDeleted );
        this.songSelected.setStatus( SongCom.DELETE );
        songSubject.onNext( this.songSelected );
    }

    public void setSongSubject(BehaviorSubject<SongCom> songSubject) {
        this.songSubject = songSubject;
    }
}
