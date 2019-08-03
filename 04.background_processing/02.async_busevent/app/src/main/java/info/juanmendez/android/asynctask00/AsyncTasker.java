package info.juanmendez.android.asynctask00;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by Juan on 5/11/2015.
 */

@EBean(scope = EBean.Scope.Singleton)
public class AsyncTasker extends AsyncTask<String[], String, String>
{
    @Bean ListAdapter adapter;
    @Bean BusHandler busHandler;

    @AfterInject
    void afterInject()
    {
        busHandler.register( this );
    }

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
                    break;
                }
            }
        }

        return "We have completed!!!";
    }

    @Override
    protected void onProgressUpdate(String... item) {

        StringEvent e = new StringEvent( item[0]);
        busHandler.requestValueChanged(e);
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
