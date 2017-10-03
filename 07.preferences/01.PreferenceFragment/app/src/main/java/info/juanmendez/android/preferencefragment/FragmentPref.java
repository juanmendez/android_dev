package info.juanmendez.android.preferencefragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Juan on 6/17/2015.
 */
public class FragmentPref extends PreferenceFragment {
    @Override
    public void onCreate( Bundle savedBundle ){

        super.onCreate(savedBundle);
        addPreferencesFromResource( R.xml.pref_screen);
    }
}