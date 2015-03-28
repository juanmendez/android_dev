package com.commonsware.android.inflation;

import android.app.Activity;
import android.os.Bundle;


public class ParentActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView( R.layout.parent_layout );
	}

}
