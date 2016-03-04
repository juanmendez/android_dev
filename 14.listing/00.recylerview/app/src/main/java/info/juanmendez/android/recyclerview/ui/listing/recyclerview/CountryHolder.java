package info.juanmendez.android.recyclerview.ui.listing.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import info.juanmendez.android.recyclerview.R;
import info.juanmendez.android.recyclerview.model.Country;
import info.juanmendez.android.recyclerview.ui.detail.WikiActivity;

/**
 * Created by Juan on 10/19/2015.
 */
public class CountryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textView;
    ImageView imageView;
    Context context;
    Country country;

    public CountryHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        textView = (TextView)  itemView.findViewById(R.id.textView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        itemView.setOnClickListener( this );
    }

    public void bindModel( Country country ){
        this.country = country;
        textView.setText(country.getName());

        Picasso.with( context ).load( country.getFlag() ).into( imageView );
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent( context, WikiActivity.class );
        i.putExtra("url", country.getLink() );
        context.startActivity( i );
    }
}