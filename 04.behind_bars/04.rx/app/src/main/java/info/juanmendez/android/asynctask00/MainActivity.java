package info.juanmendez.android.asynctask00;

import android.app.ListActivity;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rx.Observer;

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

         tasker.subscribe(new Observer<String>() {
             @Override
             public void onCompleted() {
                 Toast.makeText( MainActivity.this, "observer knows completion", Toast.LENGTH_LONG ).show();
             }

             @Override
             public void onError(Throwable e) {
                 Logging.print( e.getMessage() );
             }

             @Override
             public void onNext(String s) {
                 adapter.notifyDataSetChanged();
             }
         });

        tasker.doInBackground( items );
    }

    @Override
    public void onDestroy() {
        if (tasker != null) {
            tasker.unsubscribe();
        }

        super.onDestroy();
    }
}
