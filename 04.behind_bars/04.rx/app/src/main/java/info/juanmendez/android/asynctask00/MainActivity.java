package info.juanmendez.android.asynctask00;

import android.app.ListActivity;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends ListActivity {

    private static final String[] items= { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};

    @ViewById
    ListView list;

    @Bean
    ListAdapter adapter;

    @Bean
    AsyncTasker tasker;

    @AfterViews
    void afterViews()
    {
        setListAdapter(adapter);

         tasker.getObservable().subscribe(s -> {
            adapter.notifyDataSetChanged();
        },throwable -> {
            Logging.print( throwable.getMessage() );
        }, () -> {
            Toast.makeText( MainActivity.this, "subscriber knows completion", Toast.LENGTH_LONG ).show();
        } );

        tasker.doInBackground( items );
    }

    @Override
    public void onDestroy() {
        if (tasker != null) {
            tasker.cancel();
        }

        super.onDestroy();
    }
}
