import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import info.juanmendez.android.test.robolectricdemo.BuildConfig;
import info.juanmendez.android.test.robolectricdemo.MainActivity;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Juan on 6/28/2015.
 */
@RunWith(RobolectricTestRunner.class)
//@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml")
@Config( manifest = Config.NONE, emulateSdk = 21 )
public class ContentProviderTest
{

    private Context ctx;
    private MyOpenHelper helper;
    private SQLiteDatabase db;

    @Before
    public void  prep(){

        helper = new MyOpenHelper( RuntimeEnvironment.application.getApplicationContext() );
        db = helper.getWritableDatabase();
    }

    @Test
    public void test_00(){
        write();
        read();
    }

    private void write()
    {
        Long result;
        db = helper.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put(MyOpenHelper.NAME, "Guatemala");
        result = db.insert(MyOpenHelper.TABLE, null, row);

        row = new ContentValues();
        row.put(MyOpenHelper.NAME, "Uruguay");
        result = db.insert(MyOpenHelper.TABLE, null, row);

        row = new ContentValues();
        row.put(MyOpenHelper.NAME, "Nicaragua");
        result = db.insert(MyOpenHelper.TABLE, null, row);

        row = new ContentValues();
        row.put(MyOpenHelper.NAME, "Ecuador");
        result = db.insert(MyOpenHelper.TABLE, null, row);

        print( "finish writing rows!");
    }

    private void read()
    {
        String[] projection = { MyOpenHelper.ID, MyOpenHelper.NAME };
        db = helper.getReadableDatabase();
        Cursor c = db.query( MyOpenHelper.TABLE, projection, null, null, null, null, null );

        while( c.moveToNext() )
        {
            print( "id: " + c.getInt(c.getColumnIndexOrThrow( MyOpenHelper.ID )) + ", name: " + c.getString(c.getColumnIndexOrThrow( MyOpenHelper.NAME )));
        }

    }
    public void print( String content )
    {
        System.out.println( content );
    }
}
