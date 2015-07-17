package info.juanmendez.android.preferenceactivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Juan on 6/17/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentPref extends PreferenceFragment
{
    @Override
    public void onCreate( Bundle savedBundle ){

        super.onCreate(savedBundle);
        addPreferencesFromResource( R.xml.pref_screen);
    }
}
