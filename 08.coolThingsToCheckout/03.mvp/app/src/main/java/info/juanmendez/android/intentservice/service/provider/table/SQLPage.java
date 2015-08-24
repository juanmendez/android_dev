package info.juanmendez.android.intentservice.service.provider.table;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.juanmendez.android.intentservice.helper.PageUtil;

/**
 * Created by Juan on 7/20/2015.
 */
public class SQLPage extends SQLiteOpenHelper
{
    public static final String TABLE = "pages";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String MAG_ID = "mag_id";
    public static final String POSITION = "position";


    public SQLPage(Context context)
    {
        super(context, null, null, PageUtil.TableUtils.version );
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL( "create table "+TABLE+" ( "+ID+" integer primary key autoincrement, "+ NAME +" text not null, "+ MAG_ID + " integer not null, "+ POSITION + " integer, " +
                "CONSTRAINT mag_position UNIQUE ("+ MAG_ID + ","+ POSITION + ") )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE );

        onCreate( db );
    }
}
