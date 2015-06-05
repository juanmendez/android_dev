package com.commonsware.android.frw.filesdemo.service;

import com.commonsware.android.frw.filesdemo.model.ActionEvent;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
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

    @Bean
    BusHandler busHandler;

    @Background
    public void load_execute( File target )
    {
        try
        {
            event = new ActionEvent(ActionEvent.ActionType.LOAD, target  );
            event.setContent( load(target) );
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
    public void delete_execute( File target )
    {
        try
        {
            event = new ActionEvent(ActionEvent.ActionType.DELETE, target  );
            remove(target);
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
    public void save_execute( String text, File target )
    {
        try
        {
            event = new ActionEvent(ActionEvent.ActionType.SAVE, target  );
            event.setContent( text );
            save(text, target);
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


    private void save(String text, File target) throws IOException
    {
        FileUtils.writeStringToFile(target, text);
    }

    private String load(File target) throws IOException
    {
        String result = "";

        try
        {
            result = FileUtils.readFileToString( target );
        }
        catch (java.io.FileNotFoundException e)
        {
            // that's OK, we probably haven't created it yet
        }

        return (result);
    }

    private void remove( File target ) throws IOException
    {
        FileUtils.forceDelete( target );
    }
}
