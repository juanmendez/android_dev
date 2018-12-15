package info.juanmendez.android.db.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juan on 6/28/2015.
 */
public class OpenHelper extends SQLiteOpenHelper {
    public static final String DB = "helper";
    public static final String TABLE = "countries";
    public static final String ID = "id";
    public static final String NAME = "name";

    public OpenHelper(Context context) {
        super(context, null, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table "+TABLE+" ( "+ID+" integer primary key autoincrement, "+ NAME +" text not null )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE );

        //create one
        onCreate( db );
    }
}