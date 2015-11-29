package info.juanmendez.android.intentservice.service.provider.table;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.juanmendez.android.intentservice.helper.PageUtil;

/**
 * Created by Juan on 7/20/2015.
 */
public class SQLPage
{
    public static final String TABLE = "pages";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String MAG_ID = "mag_id";
    public static final String POSITION = "position";
}
