package info.juanmendez.android.asynctask00;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.ListView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by Juan on 5/11/2015.
 */

public class MyThread extends Thread
{
    private Handler _handler;
    private List<String> _strings;
    private Boolean _cancel = false;

    /**
     * This Thread is used by main activity to run and stop displaying a list of items..
     *
         I/asynctask﹕ new thread with string length 25
         I/asynctask﹕ stop running!
         I/asynctask﹕ new thread with string length 10
         I/asynctask﹕ thread has been canceled
         I/asynctask﹕ stop running!
         I/asynctask﹕ new thread with string length 4
         I/asynctask﹕ thread has been canceled
     *
     * @param handler
     * @param strings
     */
    MyThread(Handler handler, List<String> strings)
    {
        Logging.print( "new thread with string length " + strings.size());
        _handler = handler;
        _strings = strings;
    }


    public Boolean getCancel() {
        return _cancel;
    }

    public void setCancel() {
        Logging.print( "stop running!" );
        this._cancel = true;
    }

    @Override
    public void run(){

        for( String item: _strings )
        {
            if (!getCancel()) {

                Bundle b = new Bundle();
                b.putString( "string", item );

                Message  m = new Message();
                m.setData( b );
                _handler.sendMessage( m );
                SystemClock.sleep(400);
            }
            else{
                Logging.print( "thread has been canceled");
                break;
            }
        }
    }
}
