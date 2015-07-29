import android.database.Cursor;

import java.text.ParseException;
import java.util.List;

import info.juanmendez.android.intentservice.service.provider.SQLGlobals;
import info.juanmendez.android.intentservice.service.provider.SQLMagazine;
import info.juanmendez.android.intentservice.service.provider.SQLPage;

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
                if( type == "magazines" )
                    print( "id: " + result.getInt(result.getColumnIndexOrThrow( SQLMagazine.ID )) + ", version: " + result.getFloat(result.getColumnIndexOrThrow(SQLMagazine.VOLUME)) + ", date " +  SQLGlobals.parseDate(result.getString(result.getColumnIndexOrThrow(SQLMagazine.DATETIME))) );
                if( type == "pages" )
                    print( "id: " + result.getInt(result.getColumnIndexOrThrow( SQLPage.ID )) + " " + result.getString(result.getColumnIndexOrThrow(SQLPage.MAG_ID)) + " " + result.getString( result.getColumnIndexOrThrow(SQLPage.NAME)));
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
