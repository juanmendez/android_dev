package info.juanmendez.android.simplealarm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * This is a demo created from COMMONSWARE book as Simple alarm.
 * There have been additional code to test if an alarm is retained to one instance
 * or many upon creating several times.
 */
public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final String BOOTPREF = "bootPreference";
    CheckBox bootCkecbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        bootCkecbox = (CheckBox) findViewById(R.id.checkBootup);
        bootCkecbox.setOnCheckedChangeListener(this);

        Boolean bootPref = settings.getBoolean(BOOTPREF, true);
        bootCkecbox.setChecked( bootPref );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ){

            case R.id.start_alarm:
                WakeReceiver.startAlarm( this );
                break;

            case R.id.cancel_alarm:
                WakeReceiver.cancelAlarm(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if( settings != null )
        {
            editor = settings.edit();
            editor.putBoolean( BOOTPREF, isChecked );
            editor.commit();
        }
    }
}
