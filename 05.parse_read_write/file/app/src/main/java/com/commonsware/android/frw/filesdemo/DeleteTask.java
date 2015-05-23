package com.commonsware.android.frw.filesdemo;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.io.File;
import java.util.Date;

/**
 * Created by Juan on 5/23/2015.
 */
@EBean
public class DeleteTask
{
    @Bean
    BusHandler busHandler;

    private Exception e = null;
    private DeleteEvent event;

    @Background
    void execute( File target )
    {
        event = new DeleteEvent( target, new Date() );

        try
        {
            FileHelper.remove( target );
        }
        catch (Exception e)
        {
            this.e = e;
        }
        finally {
            onPostExecute();
        }
    }

    @UiThread
    protected void onPostExecute()
    {
        if (e != null)
        {
            busHandler.requestException(e);
        }
        else
        {
            busHandler.requestEvent( event );
        }
    }
}