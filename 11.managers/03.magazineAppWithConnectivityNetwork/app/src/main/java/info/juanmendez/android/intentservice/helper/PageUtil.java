package info.juanmendez.android.intentservice.helper;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.juanmendez.android.intentservice.model.pojo.Page;
import info.juanmendez.android.intentservice.service.provider.table.SQLPage;

/**
 * Created by Juan on 8/7/2015.
 */
public class PageUtil {

    public static ContentValues toContentValues( Page page ){
        ContentValues c = new ContentValues();
        c.put(SQLPage.POSITION, page.getPosition() );
        c.put(SQLPage.MAG_ID, page.getMagId() );
        c.put(SQLPage.NAME, page.getName() );
        return c;
    }

    public static Page fromCursor( Cursor c ){

        Page page = new Page();
        page.setId(c.getInt(c.getColumnIndexOrThrow(SQLPage.ID)));
        page.setMagId(c.getInt(c.getColumnIndexOrThrow(SQLPage.MAG_ID)));
        page.setPosition(c.getInt(c.getColumnIndexOrThrow(SQLPage.POSITION)));
        page.setName(c.getString(c.getColumnIndexOrThrow(SQLPage.NAME)));

        return page;
    }

    /**
     * Created by Juan on 7/27/2015.
     */
    public static class TableUtils
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
}
