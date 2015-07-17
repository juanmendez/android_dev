package info.juanmendez.android.customloader.service;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import info.juanmendez.android.customloader.RepoView;
import info.juanmendez.android.customloader.model.Repo;

/**
 * Created by Juan on 7/15/2015.
 */
public class RepoAdapter extends ArrayAdapter<Repo>
{

    public RepoAdapter(Context context, List<Repo> repos) {
        super(context, 0, repos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepoView row = (RepoView) convertView;

        if( row == null )
        {
            row = RepoView.inflate( parent );
        }

        row.setItem( getItem(position));
        return row;
    }
}
