package info.juanmendez.android.intentservice.service.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Juan on 7/20/2015.
 */
public class DownloadHelper extends SQLiteOpenHelper
{
    public static final String DB = "db_pages";
    public static final String TABLE = "downloads";

    public static final String ID = "id";
    public static final String VERSION = "name";
    public static final String LOCATION = "location";
    public static final String DATETIME = "datetime";

    public static final DateFormat SQLDATEFORMAT;

    static{
        SQLDATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }


    public DownloadHelper( Context context )
    {
        super(context, null, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL( "create table "+TABLE+" ( "+ID+" integer primary key autoincrement, "+ VERSION +" FLOAT not null, "+ LOCATION + " text not null, "+ DATETIME +  " DATETIME not null )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE );

        //create one
        onCreate( db );
    }
}
