package com.flexnroses.android.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver
{

	public BootReceiver()
	{

		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent )
	{
		//if airplane mode..
		if( intent.getBooleanExtra("state", false ) == true )
		{
			context.stopService( new Intent( context, MyService.class ) );
		}
		else
		{
			context.startService( new Intent( context, MyService.class ) );
		}
			
		Log.d( MyService.TAG, "onReceived" );
	}

}
