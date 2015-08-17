package com.flexnroses.android.services;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

public class BgThread extends AsyncTask <Integer, Integer, ArrayList<Integer> >
{
	private MyService service;

	public BgThread( MyService service )
	{
		this.service = service;
	}
	
	
	@Override
	protected ArrayList<Integer> doInBackground(Integer... list)
	{
		int min = list[0];
		int max = list[1];
		
		ArrayList<Integer> col = new ArrayList<Integer>();

		for( int i = min; i < max; i++  )
		{
			
			try
			{
				if( this.isCancelled() == false || this.service.is_running() == true )
				{
					Thread.sleep( 2000 );
				}
				else
				{
					break;
				}
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			col.add( i );
			this.publishProgress( i, min, max );
			
		}

		this.cancel( true );
		return col;
	}

	@Override
	protected void onCancelled()
	{

		Log.d(MyService.TAG, "CANCELED" );
		
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(ArrayList<Integer> result)
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute()
	{
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values)
	{
		
		int current = values[0];
		int min = values[1];
		int max = values[2];
		
		int percent = (int) ( (current-min) * 100 / ( max - min ) );
		
		
		Log.d(MyService.TAG, Integer.toString( percent ) );
		// TODO Auto-generated method stub
		super.onProgressUpdate(percent);
		
		
		try
		{
			this.service.setProgress( percent );
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			Log.d(MyService.TAG, e.getMessage() + e.toString() );
		}
	}
}
