package info.juanmendez.realminit.adapters.band;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import info.juanmendez.realminit.models.Band;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;

public class BandAdapter extends RealmBasedRecyclerViewAdapter<Band, BandViewHolder> {

    Band bandSelected;

    public BandAdapter(Context context, RealmResults<Band> realmResults, boolean automaticUpdate, boolean animateResults, String animateExtraColumnName) {
        super(context, realmResults, automaticUpdate, animateResults, animateExtraColumnName);
    }

    @Override
    public BandViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {

        View v = inflater.inflate( android.R.layout.simple_list_item_1, viewGroup, false);

        BandViewHolder vh = new BandViewHolder( v, this );
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(BandViewHolder holder, int position) {
        final Band myBand = realmResults.get(position);
        holder.setBand( myBand );
        holder.setSelected( myBand.equals( bandSelected ) );
    }

    public Band getBandSelected() {
        return bandSelected;
    }

    public void setBandSelected(Band _band) {

        if( bandSelected != null ){
            notifyItemChanged( realmResults.indexOf(bandSelected) );
        }

        this.bandSelected = _band;
    }

}
