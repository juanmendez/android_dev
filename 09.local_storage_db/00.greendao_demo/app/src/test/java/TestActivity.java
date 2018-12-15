import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.greenrobot.daoexample.DaoMaster;
import info.juanmendez.android.db.greendaodemo.NoteActivity;
import info.juanmendez.android.db.greendaodemo.R;
import info.juanmendez.android.db.greendaodemo.TestDaoActivity;

/**
 * Created by Juan on 6/23/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class TestActivity {


    NoteActivity activity;

    @Before
    public void setUp(){
       activity = Robolectric.buildActivity(NoteActivity.class).create().start().resume().visible().get();
    }

    @Test
    public void editText()
    {

    }
}


