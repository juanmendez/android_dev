package info.juanmendez.android.intentservice.helper;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.ParseException;

import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.model.Page;
import info.juanmendez.android.intentservice.service.provider.SQLGlobals;
import info.juanmendez.android.intentservice.service.provider.SQLMagazine;
import info.juanmendez.android.intentservice.service.provider.SQLPage;

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
}
