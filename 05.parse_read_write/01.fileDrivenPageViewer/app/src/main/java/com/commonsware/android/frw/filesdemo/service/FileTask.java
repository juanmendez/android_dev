package com.commonsware.android.frw.filesdemo.service;

import com.commonsware.android.frw.filesdemo.MainActivity;
import com.commonsware.android.frw.filesdemo.model.ActionEvent;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * I wanted to show the demo having this class separate rather than being an internal one at EditorFragment
 */
@EBean
public class FileTask
{
    private Exception _exception = null;
    private ActionEvent event;

    @RootContext
    MainActivity activity;

    @Bean
    BusHandler busHandler;

    @Background
    public void load_execute( String fileName )
    {
        try
        {
            File target = new File(activity.getFilesDir(), fileName);
            event = new ActionEvent(ActionEvent.ActionType.LOAD, target  );
            event.setContent( FileUtils.readFileToString(target) );
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

    @Background
    public void delete_execute( String fileName )
    {
        try
        {
            File target = new File(activity.getFilesDir(), fileName);
            event = new ActionEvent(ActionEvent.ActionType.DELETE, target  );
            FileUtils.forceDelete( target );
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


    @Background
    public void save_execute( String text, String fileName )
    {
        try
        {
            File target = new File(activity.getFilesDir(), fileName);
            event = new ActionEvent(ActionEvent.ActionType.SAVE, target  );
            event.setContent( text );
            FileUtils.writeStringToFile(target, text);
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
        if (_exception == null || event.getAction() == ActionEvent.ActionType.DELETE )
        {
            busHandler.requestEvent( event );
        }
        else
        {
            busHandler.requestException(_exception);
        }
    }
}
