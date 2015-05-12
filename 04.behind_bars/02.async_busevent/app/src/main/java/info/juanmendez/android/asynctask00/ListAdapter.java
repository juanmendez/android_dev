package info.juanmendez.android.asynctask00;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

/**
 * Created by Juan on 5/11/2015.
 *
 */

@EBean(scope = EBean.Scope.Singleton)
public class ListAdapter extends BaseAdapter{
    List<String> items;

    @RootContext
    Context context;

    public void setList( List<String> strings )
    {
        items = strings;
    }

    public List<String> getList()
    {
        return items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemView stringView;

        if (convertView == null) {
            stringView = ItemView_.build(context);
        } else {
            stringView = (ItemView) convertView;
        }

        stringView.bind(getItem(position));

        return stringView;
    }
}
