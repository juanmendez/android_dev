package info.juanmendez.android.asynctask00;

import android.app.Activity;
import android.app.ListFragment;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.list_fragment)
public class MyListFragment extends ListFragment {

    @ViewById
    ListView list;

    @Bean
    ListAdapter adapter;

    @Bean
    BusHandler busHandler;

    List<String> strings = new ArrayList<String>();

    @AfterViews
    void afterViews()
    {
        setRetainInstance(true);
        busHandler.register(this);
        adapter.setList( strings );
        setListAdapter( adapter );
    }

    @Override
    public void onDetach() {

        busHandler.unregister(this);
        super.onDetach();
    }


    @Subscribe
    public void onValueChanged( StringEvent event )
    {
        if( event.getContent() != null )
        {
            adapter.getList().add(event.getContent());
            adapter.notifyDataSetChanged();
        }
    }
}
