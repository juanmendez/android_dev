package info.juanmendez.android.asynctask00;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends ListActivity {

    private static final String[] items= { "lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
            "vel", "erat", "placerat", "ante", "porttitor", "sodales",
            "pellentesque", "augue", "purus" };

    @ViewById
    ListView list;

    @Bean
    ListAdapter adapter;

    @Bean
    AsyncTasker tasker;

    List<String> strings = new ArrayList<String>();

    @AfterViews
    void afterViews()
    {
        adapter.setList( strings );
        setListAdapter( adapter );
        tasker.execute( items );
    }

    @Override
    public void onDestroy() {
        if (tasker != null) {
            tasker.cancel(false);
        }

        super.onDestroy();
    }
}
