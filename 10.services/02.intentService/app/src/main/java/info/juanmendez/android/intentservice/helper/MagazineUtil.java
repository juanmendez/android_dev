package info.juanmendez.android.intentservice.helper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.model.MagazineService;
import info.juanmendez.android.intentservice.service.provider.SQLGlobals;
import info.juanmendez.android.intentservice.service.provider.SQLMagazine;

/**
 * Created by Juan on 8/2/2015.
 */
public class MagazineUtil
{
    public static ContentValues toContentValues( Magazine magazine ){

        ContentValues c = new ContentValues();

        c.put(SQLMagazine.ISSUE, magazine.getIssue());
        c.put(SQLMagazine.DATETIME, SQLGlobals.dateFormat(magazine.getDateTime()));
        c.put(SQLMagazine.LOCATION, magazine.getLocation());
        c.put(SQLMagazine.FILE_LOCATION, magazine.getFileLocation());
        c.put(SQLMagazine.TITLE, magazine.getTitle());
        c.put( SQLMagazine.STATUS, magazine.getStatus() );

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
            magazine.setStatus(c.getString(c.getColumnIndexOrThrow(SQLMagazine.STATUS)));
            magazine.setDateTime(SQLGlobals.parseDate(c.getString(c.getColumnIndexOrThrow(SQLMagazine.DATETIME))));

            return magazine;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URL getMagazineURL( String urlString, Magazine magazine ){
        try {
            URL url = new URL( urlString + magazine.getLocation() );
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Magazine clone( Magazine magazine ){

        Magazine m = new Magazine();
        overwrite( magazine, m );
        return m;
    }

    public static void overwrite( Magazine originalMagazine, Magazine copiedMagazine ){

        copiedMagazine.setIssue(originalMagazine.getIssue());
        copiedMagazine.setLocation(originalMagazine.getLocation());
        copiedMagazine.setTitle(originalMagazine.getTitle());
        copiedMagazine.setFileLocation(originalMagazine.getFileLocation());
        copiedMagazine.setDateTime(originalMagazine.getDateTime());
        copiedMagazine.setId(originalMagazine.getId());
        copiedMagazine.setStatus(originalMagazine.getStatus());
    }
}
