package com.commonsware.android.frw.filesdemo.service;

import com.commonsware.android.frw.filesdemo.model.ActionEvent;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.io.File;

/**
 * I wanted to show the demo having this class separate rather than being an internal one at EditorFragment
 */
@EBean
public class LoadTask
{
    private String _content = "";
    private Exception _exception = null;
    private File _file;

    @Bean
    BusHandler busHandler;

    @Background
    public void execute( File target )
    {
        try
        {
            _file = target;
            _content = FileHelper.load(target);
        }
        catch (Exception e)
        {
            _exception = e;
        }
        finally
        {
            onPostExecute();
        }
    }

    @UiThread
    protected void onPostExecute()
    {
        if (_exception == null)
        {
            ActionEvent event = new ActionEvent(ActionEvent.ActionType.LOAD, _file  );
            event.setContent( _content );

            busHandler.requestEvent( event );
        }
        else
        {
            busHandler.requestException(_exception);
        }
    }
}
