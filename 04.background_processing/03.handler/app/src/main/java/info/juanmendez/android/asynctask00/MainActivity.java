package info.juanmendez.android.asynctask00;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends ListActivity {


    @ViewById
    ListView list;

    @Bean
    ListAdapter adapter;

    MyThread tasker;

    List<String> strings = new ArrayList<String>();

    @AfterViews
    void afterViews()
    {
        FragmentManager fm = getFragmentManager();
        MyFragmentModel f = (MyFragmentModel) fm.findFragmentByTag( "fragmentModel");

        if( f == null )
        {
            f = new MyFragmentModel();
            fm.beginTransaction().add( f, "fragmentModel").commit();
        }


        adapter.setList( f.getList() );
        setListAdapter( adapter );

        Handler h = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();

                adapter.getList().add( data.getString("string", "?") );
                adapter.notifyDataSetChanged();
            }
        };

        tasker = new MyThread( h, f.getItems() );
        tasker.start();
    }

    @Override
    public void onDestroy() {
        if (tasker != null) {
            tasker.setCancel();
        }

        super.onDestroy();
    }
}
