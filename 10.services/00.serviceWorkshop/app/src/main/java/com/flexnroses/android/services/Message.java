package com.flexnroses.android.services;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable
{
	private String action;
	private Integer percent;
	
	public Message( Parcel in )
	{

		action = in.readString();
		percent = in.readInt();
		// TODO Auto-generated constructor stub
	}
	
	public String getAction()
	{
	
		return action;
	}

	public void setAction(String action)
	{
	
		this.action = action;
	}

	public Integer getPercent()
	{
	
		return percent;
	}

	public void setPercent(Integer percent)
	{
	
		this.percent = percent;
	}

	@Override
	public int describeContents()
	{

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1)
	{
		out.writeString( this.action );
		out.writeInt( this.percent );

	}
	
	public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() { //
		public Message createFromParcel(Parcel source) 
		{
			return new Message(source);
		}
		
		public Message[] newArray(int size) 
		{
			return new Message[size];
		}
	};

}
