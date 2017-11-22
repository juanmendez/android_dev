package info.juanmendez.android.simplealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.evernote.android.job.JobManager;

import info.juanmendez.android.simplealarm.androidjob.DemoExactTimeJob;
import timber.log.Timber;

import static info.juanmendez.android.simplealarm.MainActivity.AlarmBroadcaster.ALARM_BROADCAST_ACTION;

/**
 * This is a demo created from COMMONSWARE book as Simple alarm.
 * There have been additional code to test if an alarm is retained to one instance
 * or many upon creating several times.
 */
public class MainActivity extends AppCompatActivity {
    private static final long  PERIOD= 5 * 60 * 1000;
    private TextView statusText;


    private AlarmBroadcaster broadcaster;
    private static final String START_JOB = "startJob";
    private long start;

    private static final String JOB_ID = "lastJobId";
    private int currentJobId = 0;
    private static final String STATUS = "status";
    private String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( savedInstanceState != null ){
            currentJobId = savedInstanceState.getInt( JOB_ID );
            start = savedInstanceState.getLong( START_JOB);
            status = savedInstanceState.getString(STATUS);
        }

        statusText = (TextView) findViewById(R.id.statusText);
        statusText.setText( status );
        startBroadcaster();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(JOB_ID, currentJobId );
        outState.putLong( START_JOB, start );
        outState.putString( STATUS, status );
        super.onSaveInstanceState(outState);
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

        start = SystemClock.elapsedRealtime();
        currentJobId = DemoExactTimeJob.scheduleJobAtAGivenTime( PERIOD, PERIOD  + (5*60*1000L) );

        startBroadcaster();
    }

    public void cancelAlarm(){
        JobManager.instance().cancelAllForTag(DemoExactTimeJob.TAG);
        cancelBroadcaster();
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelBroadcaster();
    }

    private void startBroadcaster(){
        registerReceiver( broadcaster = new AlarmBroadcaster(), new IntentFilter(ALARM_BROADCAST_ACTION));
    }

    private void cancelBroadcaster(){
        if( broadcaster != null ){
            unregisterReceiver( broadcaster );
            broadcaster = null;
        }
    }

    public class AlarmBroadcaster extends BroadcastReceiver {
        public static final String ALARM_BROADCAST_ACTION = "alarmBroadcastAction";

        @Override
        public void onReceive(Context context, Intent intent) {
            long secondsPassed = (( SystemClock.elapsedRealtime()-start )/1000);
            status = "activity result. " + secondsPassed + " vs " + PERIOD/1000 + " diff " + ( (secondsPassed*1000) - PERIOD);
            statusText.setText( status );
            Timber.i( status);
            start = SystemClock.elapsedRealtime();
            Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
        }
    }
}