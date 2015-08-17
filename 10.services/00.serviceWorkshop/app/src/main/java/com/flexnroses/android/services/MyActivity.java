package com.flexnroses.android.services;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyActivity extends Activity implements OnClickListener 
{
	private Button btnStart;
	private Button btnStop;
	private ServiceReceiver receiver;
	
	@Override
	protected void onPause()
	{

		// TODO Auto-generated method stub
		super.onPause();
		this.unregisterReceiver( this.receiver );
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		IntentFilter filter = new IntentFilter( MyService.TAG );
		this.registerReceiver(this.receiver, filter);
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnStart = (Button) this.findViewById(R.id.btnStartService);
        btnStop = (Button) this.findViewById(R.id.btnStopService );
        
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    
        this.receiver = new ServiceReceiver();
	}
	
	@Override
	public void onClick(View arg0)
	{
		Button button = (Button) arg0;

		// TODO Auto-generated method stub
		
		if( button == this.btnStart )
		{
			Intent intent = new Intent(this, MyService.class );
			
			//.2 add attributes
			intent.putExtra( "creator", this.getClass().getName() );
			intent.putExtra( "weather", "77 F" );
			
			this.startService( intent );
		}
		else
		if( button == this.btnStop )
		{
			this.stopService( new Intent(this, MyService.class ) );
		}
		
	}
	
	private class ServiceReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{

			//Log.d("MyActivity", "percent " + intent.getExtras().getInt("percent") );
			Message m = intent.getExtras().getParcelable("myparcel" );
			Log.d(MyService.TAG, m.getAction() +" " + m.getPercent() );
			
		}
		
	}
}