import android.database.Cursor;

import java.text.ParseException;
import java.util.List;

import info.juanmendez.android.intentservice.service.provider.DownloadHelper;
import info.juanmendez.android.intentservice.service.provider.PageHelper;

/**
 * Created by Juan on 7/2/2015.
 */
public class Log
{
     public static void print( String type, Cursor result )
    {
        while( result.moveToNext() )
        {
            try {
                if( type == "download" )
                    print( "id: " + result.getInt(result.getColumnIndexOrThrow( DownloadHelper.ID )) + ", version: " + result.getFloat(result.getColumnIndexOrThrow(DownloadHelper.VERSION)) + ", date " +  DownloadHelper.SQLDATEFORMAT.parse( result.getString(result.getColumnIndexOrThrow(DownloadHelper.DATETIME))).toString() );
                if( type == "page" )
                    print( "id: " + result.getInt(result.getColumnIndexOrThrow( PageHelper.ID )) + " " + result.getString( result.getColumnIndexOrThrow(PageHelper.LOCATION)) );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void print ( String content )
    {
        System.out.println( content );
    }


    public static void print ( List<String> list ){

        for( String i : list ){
            print( i );
        }
    }
}
