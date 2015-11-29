package info.juanmendez.android.intentservice.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.ui.MagazineRow;

/**
 * Created by Juan on 8/1/2015.
 */
public class MagazineAdapter extends ArrayAdapter<Magazine>
{
    List<Magazine> magazines = new ArrayList<Magazine>();
    public MagazineAdapter(Context context,List<Magazine> list) {
        super(context, 0, list);
        magazines = list;
    }

    @Override
    public Magazine getItem(int position) {
        return magazines.get(position);
    }

    @Override
    public int getPosition(Magazine item) {
        return magazines.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = MagazineRow.inflate( parent );

        Magazine magazine = getItem(position);
        ((MagazineRow) convertView).setItem(magazine);

        return convertView;
    }

    public void addAll( ArrayList<Magazine> list ){
        magazines.clear();
        magazines.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return magazines.size();
    }
}
