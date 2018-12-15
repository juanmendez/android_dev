package info.juanmendez.realminit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.juanmendez.realminit.models.Band;

/**
 * Created by musta on 8/28/2016.
 */
public class BandAdapter extends RecyclerView.Adapter<BandViewHolder> {

    List<Band> bands;
    Band bandSelected;

    public BandAdapter(List<Band> _bands) {
        bands = _bands;
    }

    @Override
    public BandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate( android.R.layout.simple_list_item_1, parent, false);

        BandViewHolder vh = new BandViewHolder( v, this );
        return vh;
    }

    @Override
    public void onBindViewHolder(BandViewHolder holder, int position) {
        Band myBand = bands.get(position);
        holder.setBand( myBand );
        holder.setSelected( myBand.equals( bandSelected ) );
    }

    @Override
    public int getItemCount() {
        return bands.size();
    }

    public Band getBandSelected() {
        return bandSelected;
    }

    public void setBandSelected(Band _band) {

        if( bandSelected != null ){
            notifyItemChanged( bands.indexOf(bandSelected) );
        }

        this.bandSelected = _band;
    }
}
