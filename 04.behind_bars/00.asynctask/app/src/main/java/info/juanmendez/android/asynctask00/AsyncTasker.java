package info.juanmendez.android.asynctask00;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ListView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by Juan on 5/11/2015.
 */

@EBean
public class AsyncTasker extends AsyncTask<String[], String, String>
{
    @Bean ListAdapter adapter;
    private String[] strings;

    @Override
    protected String doInBackground(String[]... stringArray) {

        for( String[] myStrings: stringArray )
        {
            for( String item: myStrings )
            {
                if (!isCancelled()) {
                    publishProgress(item);
                    SystemClock.sleep(400);
                }
                else{
                    Logging.print( "asynctask has been canceled");
                }

            }
        }

        return "We have completed!!!";
    }

    @Override
    protected void onProgressUpdate(String... item) {
        adapter.getList().add(item[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute( String status )
    {
        if( status != null )
            Logging.print( status );
        else
            Logging.print( "onPostExecute, missing string status");
    }
}
