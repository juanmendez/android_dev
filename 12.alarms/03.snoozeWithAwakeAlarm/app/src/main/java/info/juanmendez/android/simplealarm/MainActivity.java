package info.juanmendez.android.simplealarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * This is a demo created from COMMONSWARE book as Simple alarm.
 * There have been additional code to test if an alarm is retained to one instance
 * or many upon creating several times.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
