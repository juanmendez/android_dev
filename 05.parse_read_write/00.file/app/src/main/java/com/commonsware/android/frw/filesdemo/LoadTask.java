package com.commonsware.android.frw.filesdemo;

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
    private String result = "";
    private Exception e = null;

    @Bean
    BusHandler busHandler;

    @Background
    void execute( File target )
    {
        try
        {
            result = FileHelper.load(target);
        }
        catch (Exception e)
        {
            this.e = e;
        }
        finally
        {
            onPostExecute();
        }

    }

    @UiThread
    protected void onPostExecute()
    {
        if (e == null)
        {
            busHandler.requestContent( result );
        }
        else
        {
            busHandler.requestException( e );
        }
    }
}
