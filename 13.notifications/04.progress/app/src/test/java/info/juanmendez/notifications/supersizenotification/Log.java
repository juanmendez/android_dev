package info.juanmendez.notifications.supersizenotification;

import java.util.List;

/**
 * Created by Juan on 7/2/2015.
 */
public class Log
{

    public static void print ( String content )
    {
        System.out.println( content );
    }

    public static void print ( List<String> list ){

        for( String i : list ){
            print( i );
        }
    }

    public static void print( Integer i )
    {
        print( Integer.toString(i));
    }

    public static void print( Float f )
    {
        print( Float.toString(f));
    }
}
