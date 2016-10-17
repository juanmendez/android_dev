package info.juanmendez.realminit.adapters.song;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import info.juanmendez.realminit.models.Song;
import io.realm.RealmViewHolder;

/**
 * Created by Juan Mendez on 9/8/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
public class SongViewHolder extends RealmViewHolder {
    private SongAdapter adapter;
    private TextView textView;
    private Song song;

    public SongViewHolder(View _itemView, SongAdapter _adapter) {
        super(_itemView);

        textView = (TextView) _itemView;
        adapter = _adapter;

        textView.setOnClickListener(view -> {
            if( song!= null ){
                adapter.setSongSelected( song );
                setSelected( true );
            }
        });

        textView.setOnLongClickListener(view->{
            adapter.setSongDeleted( song );
            return true;
        });
    }

    public Song getSong(){
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
        textView.setText( song.getTitle() );
    }

    public void setSelected(boolean isSelected ){

        if( isSelected ){
            textView.setBackgroundColor(Color.RED );
        }else{
            textView.setBackgroundColor(Color.WHITE);
        }
    }
}
