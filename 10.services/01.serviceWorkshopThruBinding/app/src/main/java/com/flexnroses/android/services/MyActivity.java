package com.flexnroses.android.services;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyActivity extends Activity implements OnClickListener
{
    private Button btnStart;
    private Button btnStop;
    private Button btnGetPercent;
    private ServiceReceiver receiver;
    private MyService myService;

    @Override
    protected void onPause()
    {

        // TODO Auto-generated method stub
        super.onPause();
        this.unregisterReceiver(this.receiver);

        /*
        if( bound )
            unbindService( connection );*/
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();

        IntentFilter filter = new IntentFilter( MyService.TAG );
        this.registerReceiver(this.receiver, filter);

        if( !bound )
        {
            Intent intent = new Intent(this, MyService.class );
            bindService(intent, connection, BIND_AUTO_CREATE);
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnStart = (Button) this.findViewById(R.id.btnStartService);
        btnStop = (Button) this.findViewById(R.id.btnStopService );
        btnGetPercent = (Button) findViewById(R.id.btnGetPercent);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnGetPercent.setOnClickListener(this);

        this.receiver = new ServiceReceiver();
    }

    @Override
    public void onClick(View arg0)
    {
        Button button = (Button) arg0;

        // TODO Auto-generated method stub

        if( button == this.btnStart  && bound )
        {
            //start running
            Intent intent = new Intent();
            intent.putExtra( "creator", this.getClass().getName() );
            intent.putExtra("weather", "77 F");
            myService.start(intent);
        } else if( button == this.btnStop && bound )
        {
            myService.stop();
            //this.stopService( new Intent(this, MyService.class ) );
        }
        else
        if( button == this.btnGetPercent )
        {
            if( myService != null  )
            {
                Log.d(MyService.TAG, Integer.toString(myService.getPercent()));
            }
        }

    }

    private class ServiceReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            //Log.d("MyActivity", "percent " + intent.getExtras().getInt("percent") );
            Message m = intent.getExtras().getParcelable("myparcel" );
            Log.d(MyService.TAG, m.getAction() + " " + m.getPercent());

        }

    }

    private Boolean bound = false;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bound = true;
            MyLocalBinder binder = (MyLocalBinder) service;
            myService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
            bound = false;
        }
    };
}