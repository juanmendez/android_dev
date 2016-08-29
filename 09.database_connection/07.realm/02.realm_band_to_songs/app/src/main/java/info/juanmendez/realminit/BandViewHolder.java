package info.juanmendez.realminit;

import android.graphics.Color;
import android.support.annotation.BoolRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.juanmendez.realminit.models.Band;

/**
 * Created by musta on 8/28/2016.
 */
public class BandViewHolder extends RecyclerView.ViewHolder {

    private BandAdapter adapter;
    private TextView textView;
    private Band band;

    public BandViewHolder(View _itemView, BandAdapter _adapter) {
        super(_itemView);
        textView = (TextView) _itemView;
        adapter = _adapter;

        textView.setOnClickListener(view -> {
            if( band!= null ){
                adapter.setBandSelected( band );
                setSelected( true );
            }
        });
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
        textView.setText( band.getName() );
    }

    public void setSelected(boolean isSelected ){

        if( isSelected ){
            textView.setBackgroundColor(Color.RED );
        }else{
            textView.setBackgroundColor(Color.WHITE);
        }
    }
}
