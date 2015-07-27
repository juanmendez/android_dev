package info.juanmendez.android.intentservice.service.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juan on 7/20/2015.
 */
public class PageHelper extends SQLiteOpenHelper
{
    public static final String DB = "db_pages";
    public static final String TABLE = "pages";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String LOCATION = "location";


    public PageHelper( Context context )
    {
        super(context, null, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL( "create table "+TABLE+" ( "+ID+" integer primary key autoincrement, "+ NAME +" text not null, "+ LOCATION + " text not null )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE );

        //create one
        onCreate( db );
    }
}
