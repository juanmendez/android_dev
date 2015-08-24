package info.juanmendez.android.intentservice.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

import info.juanmendez.android.intentservice.R;

/**
 * Created by Juan on 8/24/2015.
 */
public class PrefActivity extends PreferenceActivity
{
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate( Bundle savedInstanceState){
        super.onCreate( savedInstanceState );

        addPreferencesFromResource(R.xml.pref_screen);
    }
}
