package info.juanmendez.android.recyclerview.ui.listing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import info.juanmendez.android.recyclerview.App;
import info.juanmendez.android.recyclerview.R;
import info.juanmendez.android.recyclerview.model.Country;
import info.juanmendez.android.recyclerview.ui.listing.recyclerview.CountryAdapter;

/**
 * Created by Juan on 10/25/2015.
 */
public class CountriesFragment extends Fragment
{
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.countries_layout, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration decor = new HorizontalDividerItemDecoration.Builder(getContext()).
                color(getResources().getColor(R.color.colorPrimary)).build();

        mRecyclerView.addItemDecoration(decor);

        //mRecyclerView.setLayoutManager( new GridLayoutManager(this, 2 ));
        ArrayList<Country> countries = getCountries();
        mRecyclerView.setAdapter(new CountryAdapter(inflater, getCountries()));

        App app = (App) getActivity().getApplication();

        if( app.getObservable().getCountry() == null ){
            app.getObservable().emit( getCountries().get(0) );
        }

        return view;
    }

    private ArrayList<Country> getCountries(){
        String[] countries =  getResources().getStringArray(R.array.countries);
        String[] links = getResources().getStringArray(R.array.links);
        String[] flags = getResources().getStringArray(R.array.flags);
        ArrayList<Country> list = new ArrayList<Country>();

        for( int i = 0; i < countries.length; i++ )
        {
            list.add( new Country( countries[i], links[i], flags[i]));
        }

        return list;
    }
}