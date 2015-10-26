package info.juanmendez.android.recyclerview.ui.listing.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import info.juanmendez.android.recyclerview.R;
import info.juanmendez.android.recyclerview.model.Country;

/**
 * Created by Juan on 10/19/2015.
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryHolder> {

    LayoutInflater inflater;
    ArrayList<Country> countries;

    public CountryAdapter(LayoutInflater inflater, ArrayList<Country> countries )
    {
        this.inflater = inflater;
        this.countries = countries;
    }

    @Override
    public CountryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountryHolder( inflater.inflate(R.layout.country_item, parent, false) );
    }

    @Override
    public void onBindViewHolder(CountryHolder holder, int position) {
        holder.bindModel( countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
