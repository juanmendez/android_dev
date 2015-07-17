package info.juanmendez.android.contentloader;

import android.database.Cursor;

import java.util.List;

/**
 * Created by Juan on 7/2/2015.
 */
public class Log
{
    public static void print( Cursor result )
    {
        while( result.moveToNext() )
        {
            print( "id: " + result.getInt(result.getColumnIndexOrThrow( OpenHelper.ID )) + ", name: " + result.getString(result.getColumnIndexOrThrow( OpenHelper.NAME )));
        }
    }

    public static void print( String message, Cursor result )
    {
        print( message );
        print( result );
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
