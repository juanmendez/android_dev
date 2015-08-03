package info.juanmendez.android.intentservice.service.provider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Juan on 7/27/2015.
 */
public class SQLGlobals
{
    public static final int version = 2;

    private static final DateFormat SQLITE_DATEFORMAT;

    static{
        SQLITE_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static String dateFormat(Date date){
        return SQLITE_DATEFORMAT.format( date );
    }

    public static Date parseDate(String dateString) throws ParseException {
        return SQLITE_DATEFORMAT.parse( dateString );
    }
}
