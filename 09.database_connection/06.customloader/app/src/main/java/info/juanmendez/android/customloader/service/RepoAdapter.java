package info.juanmendez.android.customloader.service;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import info.juanmendez.android.customloader.RepoView;
import info.juanmendez.android.customloader.model.Repo;

/**
 * Created by Juan on 7/15/2015.
 */
public class RepoAdapter extends ArrayAdapter<Repo>
{
    private List<Repo> repos;

    public RepoAdapter(Context context, List<Repo> list) {
        super(context, 0, list);
        repos =  list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepoView row = (RepoView) convertView;

        if( row == null )
        {
            row = RepoView.inflate( parent );
        }

        row.setItem(getItem(position));
        return row;
    }

    public void setRepos(List<Repo> repos) {
        this.repos.clear();
        this.repos.addAll(repos);
        this.notifyDataSetChanged();
    }

}
