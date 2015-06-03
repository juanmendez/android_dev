package com.commonsware.android.frw.filesdemo.service;

import com.commonsware.android.frw.filesdemo.MainActivity;
import com.commonsware.android.frw.filesdemo.model.Page;
import com.commonsware.android.frw.filesdemo.utils.Logging;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONComposer;
import com.fasterxml.jackson.jr.ob.comp.ArrayComposer;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Juan on 5/23/2015.
 */
@EBean
public class BuildTask
{
    @Bean
    BusHandler busHandler;

    @RootContext
    MainActivity activity;

    private Exception e = null;

    @Background
    public void execute()
    {
        List<Page> list;

        try
        {
            list = new ArrayList<Page>();
            Page p = new Page( "hello1.txt", new Date(), "hello1", "hola como estas" );
            list.add(p);

            p = new Page( "hello2.txt", new Date(), "hello2", "hola como estas" );
            list.add(p);

            p = new Page( "hello3.txt", new Date(), "hello3", "hola como estas" );
            list.add(p);

            p = new Page( "hello4.txt", new Date(), "hello4", "hola como estas" );
            list.add(p);

            p = new Page( "hello5.txt", new Date(), "hello5", "hola como estas" );
            list.add(p);

            JSON json = JSON.std;
            JSONComposer composer = json.with(JSON.Feature.PRETTY_PRINT_OUTPUT ).composeString();
            ArrayComposer arrayComposer = composer.startArray();


            for( Page page: list)
            {
                arrayComposer.startObject().
                        put("title", page.getTitle()).
                        put("dateCreated", page.getDateCreated().getTime()).
                        put("fileName", page.getFileName()).
                        put("content", page.getContent() ).end();
            }


            arrayComposer.end();
            String myJsonString = composer.finish().toString();
            //myJsonString = "[{\"title\":\"hello1\",\"dateCreated\":1433000488467,\"fileName\":\"hello1.txt\",\"content\":\"hola como estas\"},{\"title\":\"hello2\",\"dateCreated\":1433000488467,\"fileName\":\"hello2.txt\",\"content\":\"hola como estas\"},{\"title\":\"hello3\",\"dateCreated\":1433000488467,\"fileName\":\"hello3.txt\",\"content\":\"hola como estas\"},{\"title\":\"hello4\",\"dateCreated\":1433000488467,\"fileName\":\"hello4.txt\",\"content\":\"hola como estas\"},{\"title\":\"hello5\",\"dateCreated\":1433000488467,\"fileName\":\"hello5.txt\",\"content\":\"hola como estas\"}]";


            File file = new File( activity.getFilesDir(), "pages.json" );
            FileHelper.save(myJsonString, file);

            myJsonString = FileHelper.load(file);
            //.print( myJsonString );

            //myJsonString = "[{\"title\":\"hello1\",\"dateCreated\":1433000488467,\"fileName\":\"hello1.txt\",\"content\":\"hola como estas\"},{\"title\":\"hello2\",\"dateCreated\":1433000488467,\"fileName\":\"hello2.txt\",\"content\":\"hola como estas\"},{\"title\":\"hello3\",\"dateCreated\":1433000488467,\"fileName\":\"hello3.txt\",\"content\":\"hola como estas\"},{\"title\":\"hello4\",\"dateCreated\":1433000488467,\"fileName\":\"hello4.txt\",\"content\":\"hola como estas\"},{\"title\":\"hello5\",\"dateCreated\":1433000488467,\"fileName\":\"hello5.txt\",\"content\":\"hola como estas\"}]";

            list = JSON.std.listOfFrom(Page.class, myJsonString);

            Logging.print(  "title is " + list.get(0).getTitle() );


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
    }
}