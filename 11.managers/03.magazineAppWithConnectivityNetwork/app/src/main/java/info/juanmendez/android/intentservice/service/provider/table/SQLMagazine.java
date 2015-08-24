package info.juanmendez.android.intentservice.service.provider.table;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.juanmendez.android.intentservice.helper.PageUtil;

/**
 * Created by Juan on 7/20/2015.
 */
public class SQLMagazine
{
    public static final String TABLE = "magazines";

    public static final String ID = "id";
    public static final String ISSUE = "issue";
    public static final String LOCATION = "location";
    public static final String TITLE = "title";
    public static final String FILE_LOCATION = "file_location";
    public static final String DATETIME = "datetime";
    public static final String STATUS = "status";
}
