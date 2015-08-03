package info.juanmendez.android.intentservice.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import info.juanmendez.android.intentservice.ListMagazines;
import info.juanmendez.android.intentservice.MagazineRow;
import info.juanmendez.android.intentservice.R;

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

        if( convertView == null ){
            convertView = MagazineRow.inflate( parent );
        }

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
