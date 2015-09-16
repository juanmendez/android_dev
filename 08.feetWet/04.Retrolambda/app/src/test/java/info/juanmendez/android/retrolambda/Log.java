package info.juanmendez.android.retrolambda;

import java.util.List;

/**
 * Created by Juan on 7/2/2015.
 */
public class Log
{

    public static void print( Integer integro ){
        System.out.println( Integer.toString(integro));
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
