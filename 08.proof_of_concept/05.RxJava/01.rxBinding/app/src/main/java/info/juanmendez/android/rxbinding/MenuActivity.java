package info.juanmendez.android.rxbinding;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import hugo.weaving.DebugLog;

/**
 * Created by Juan on 12/7/2015.
 */
@EActivity
@OptionsMenu(R.menu.menu)
public class MenuActivity extends AppCompatActivity{

    @AfterViews
    void afterViews(){

    }

    @OptionsItem
    void preferences(){
        printArgs( "you clicked on preferences");
    }

    @DebugLog
    private void printArgs(String... args) {
        for (String arg : args) {
            Log.i("Args", arg);
        }
    }

}
