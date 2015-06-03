package com.commonsware.android.frw.filesdemo.service;

import com.commonsware.android.frw.filesdemo.model.ActionEvent;
import com.commonsware.android.frw.filesdemo.model.ActionEvent.ActionType;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.io.File;

/**
 * Created by Juan on 5/23/2015.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DeleteTask
{
    @Bean
    BusHandler busHandler;

    private Exception e = null;
    private ActionEvent event;


    @Background
    public void execute( File target )
    {
        event = new ActionEvent( ActionType.DELETE, target );

        try
        {
            FileHelper.remove(target);
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