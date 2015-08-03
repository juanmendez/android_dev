package info.juanmendez.android.intentservice.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import java.text.ParseException;
import java.util.Date;

import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.service.provider.SQLGlobals;
import info.juanmendez.android.intentservice.service.provider.SQLMagazine;

/**
 * Created by Juan on 8/2/2015.
 */
public class MagazineParser
{
    public static ContentValues toContentValues( Magazine magazine ){

        ContentValues c = new ContentValues();
        c.put(SQLMagazine.ISSUE, magazine.getIssue());
        c.put(SQLMagazine.DATETIME, SQLGlobals.dateFormat(magazine.getDateTime()));
        c.put(SQLMagazine.LOCATION, magazine.getLocation());
        c.put(SQLMagazine.FILE_LOCATION, magazine.getFileLocation());
        c.put(SQLMagazine.TITLE, magazine.getTitle());

        return c;
    }

    public static Magazine fromCursor(  Cursor c ){

        try {
            Magazine magazine = new Magazine();
            magazine.setId(c.getInt(c.getColumnIndexOrThrow(SQLMagazine.ID)));
            magazine.setIssue(c.getFloat(c.getColumnIndexOrThrow(SQLMagazine.ISSUE)));
            magazine.setLocation(c.getString(c.getColumnIndexOrThrow(SQLMagazine.LOCATION)));
            magazine.setFileLocation(c.getString(c.getColumnIndexOrThrow(SQLMagazine.FILE_LOCATION)));
            magazine.setTitle(c.getString(c.getColumnIndexOrThrow(SQLMagazine.TITLE)));
            magazine.setDateTime(SQLGlobals.parseDate(c.getString(c.getColumnIndexOrThrow(SQLMagazine.DATETIME))));

            return magazine;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Magazine fromBundle( Bundle b ){

        Magazine magazine = new Magazine();
        magazine.setId( b.getInt( SQLMagazine.ID ));
        magazine.setIssue(  b.getFloat(SQLMagazine.ISSUE, 0f ));
        magazine.setLocation("file://" + b.getString(SQLMagazine.LOCATION, ""));

        return magazine;
    }

}
