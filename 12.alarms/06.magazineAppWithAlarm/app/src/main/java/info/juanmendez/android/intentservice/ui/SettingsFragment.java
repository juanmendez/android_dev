package info.juanmendez.android.intentservice.ui;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.model.MagazinesPref;
import info.juanmendez.android.intentservice.service.alarm.WakeReceiver;

/**
 * Created by Juan on 8/31/2015.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_screen);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
    }

    private Application getApplication(){
        return getActivity().getApplication();
    }

    @Override
    public void onResume(){
        super.onResume();

        if( sharedPreferences != null )
            sharedPreferences.registerOnSharedPreferenceChangeListener( this );
    }

    @Override
    public void onPause(){
        super.onPause();

        if( sharedPreferences != null )
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        if( key.equals(MagazinesPref.MAGAZINE_UPDATES) ){
            Boolean updates = sharedPreferences.getBoolean(MagazinesPref.MAGAZINE_UPDATES, false );

            if( updates ){
                WakeReceiver.startAlarm(this.getApplication());
            }else{
                WakeReceiver.cancelAlarm( this.getApplication() );
            }
        }
    }
}
