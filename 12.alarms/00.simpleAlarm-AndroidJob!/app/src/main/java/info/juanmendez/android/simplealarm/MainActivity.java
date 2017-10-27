package info.juanmendez.android.simplealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import info.juanmendez.android.simplealarm.androidjob.DemoSyncJob;
import timber.log.Timber;

/**
 * There have been additional code to test if an alarm is retained to one instance
 * or many upon creating several times.
 */
public class MainActivity extends AppCompatActivity {
    private static final int ALARM_ID=1337;
    private static final int PERIOD=60000;
    private PendingIntent pi=null;
    private AlarmManager mgr=null;
    private TextView statusText;
    private long start = SystemClock.elapsedRealtime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusText = (TextView) findViewById(R.id.statusText);
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
                startAlarm();
                return true;

            case R.id.cancel_alarm:
                cancelAlarm();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAlarm(){

        DemoSyncJob.scheduleJob();
        /*mgr=(AlarmManager)getSystemService(ALARM_SERVICE);
        pi=createPendingResult(ALARM_ID, new Intent(), 0);*/

        /**
         * Based on commonsware book up to targetSdkVersion 18 the alarm behavior is
         * time precise, where from version 19 has an inexact behavior. Next demo will
         * be aiming for using the precise time.. why not test this app in a version older than 19?
         */

        /*start = SystemClock.elapsedRealtime();
        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + PERIOD, PERIOD, pi);*/

        statusText.setText(getString(R.string.start));
    }

    public void cancelAlarm(){
        Timber.i( "canceled");
        /*mgr=(AlarmManager)getSystemService(ALARM_SERVICE);

        if( mgr != null && pi != null ){
            mgr.cancel(pi);
            statusText.setText(getString(R.string.end));
        }else{
            statusText.setText( "there was no alarm to cancel!");
        }*/
    }

    @Override
    public void onDestroy() {
        cancelAlarm();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALARM_ID) {
            long secondsPassed = (( SystemClock.elapsedRealtime()-start )/1000);
            Timber.i("activity result. " + secondsPassed + " vs " + PERIOD/1000 + " diff " + ( (secondsPassed*1000) - PERIOD));
            start = SystemClock.elapsedRealtime();
            Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show();
        }
    }
}
