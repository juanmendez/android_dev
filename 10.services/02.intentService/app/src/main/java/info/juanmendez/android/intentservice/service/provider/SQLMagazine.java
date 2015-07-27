package info.juanmendez.android.intentservice.service.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Juan on 7/20/2015.
 */
public class SQLMagazine extends SQLiteOpenHelper
{
    public static final String TABLE = "magazines";

    public static final String ID = "id";
    public static final String VERSION = "name";
    public static final String LOCATION = "location";
    public static final String DATETIME = "datetime";

    public SQLMagazine(Context context)
    {
        super(context, null, null, SQLGlobals.version );
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
