package info.juanmendez.android.recyclerview.ui.listing.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import info.juanmendez.android.recyclerview.App;
import info.juanmendez.android.recyclerview.R;
import info.juanmendez.android.recyclerview.model.Country;
import info.juanmendez.android.recyclerview.rx.UIObservable;
import rx.Subscription;

/**
 * Created by Juan on 10/19/2015.
 */
public class CountryHolder extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;
    Context context;
    Country country;
    Subscription subscription;

    public CountryHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        textView = (TextView)  itemView.findViewById(R.id.textView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        itemView.setOnClickListener(v -> {
            getObservable().emit(country);
        });
    }

    public void bindModel( Country country ){
        this.country = country;
        textView.setText(country.getName());
        Picasso.with( context ).load( country.getFlag() ).into(imageView);

itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
    @Override
    public void onViewAttachedToWindow(View v) {
        subscription = getObservable().subscribe(countrySelected -> {

            if (countrySelected.getName().equals( country.getName())) {
                ((LinearLayout)itemView).setBackgroundColor(Color.YELLOW );
            }
            else{
                ((LinearLayout)itemView).setBackgroundColor(Color.WHITE );
            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        if (subscription != null) {
            getObservable().unsubscribe(subscription);
            subscription = null;
        }
    }
});
    }

    private UIObservable getObservable(){

        return ((App) context.getApplicationContext()).getObservable();
    }
}
