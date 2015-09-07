package info.juanmendez.android.intentservice.service.provider.table;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.juanmendez.android.intentservice.helper.PageUtil;

/**
 * Created by Juan on 8/24/2015.
 */
public class SqlHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public SqlHelper(Context context)
    {
        super(context, PageUtil.DB, null, PageUtil.TableUtils.version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL( "CREATE TABLE IF NOT EXISTS "+SQLMagazine.TABLE+" ( "+SQLMagazine.ID+" integer primary key autoincrement, "+
                    SQLMagazine.ISSUE +" FLOAT not null UNIQUE, "+ SQLMagazine.LOCATION + " text not null, " + SQLMagazine.FILE_LOCATION +
                    " text, " + SQLMagazine.TITLE + " text, " + SQLMagazine.STATUS + " text, " + SQLMagazine.DATETIME +  " DATETIME not null )");

            db.execSQL( "CREATE TABLE IF NOT EXISTS "+ SQLPage.TABLE+" ( "+ SQLPage.ID+" integer primary key autoincrement, "+ SQLPage.NAME +" text not null, "+
                    SQLPage.MAG_ID + " integer not null, "+ SQLPage.POSITION + " integer, " +
                    "CONSTRAINT mag_position UNIQUE ("+ SQLPage.MAG_ID + ","+ SQLPage.POSITION + ") )");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + SQLMagazine.TABLE );
        db.execSQL( "DROP TABLE IF EXISTS " + SQLPage.TABLE );
        //create one
        onCreate( db );
    }
}
