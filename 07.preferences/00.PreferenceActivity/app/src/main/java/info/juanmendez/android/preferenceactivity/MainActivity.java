package info.juanmendez.android.preferenceactivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.List;

import utils.Logging;
import utils.PrefSupport;

public class MainActivity extends PreferenceActivity {

    Boolean isSinglePane = false;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( isSinglePane == true )
            addPreferencesFromResource( R.xml.pref_screen);

        tellPrefences();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        if( PrefSupport.isSinglePane(this))
        {
            isSinglePane = true;
        }
        else
        {
            loadHeadersFromResource( R.xml.headers, target);
        }
    }

    /**
     * This is something new to me, I was told by the debugger this method was required to override.
     * @param fragmentName the fragment + its path
     * @return true, if you want to authenticate loading it, or not.. which would crash!
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {

        String leftFragmentClass = FragmentPref.class.toString();
        return leftFragmentClass.indexOf(fragmentName) >= 0 ;
    }

    private void tellPrefences()
    {
        Context context = getApplicationContext();

        /**
         * It uses getSharedPreferences who
         */
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);

        Boolean checked = settings.getBoolean( "checkbox_id", false );
        Logging.print( "checked box "  + checked );

        String lang = settings.getString( "fav_language", "no language" );
        Logging.print( "fav language " + lang );

        String quote = settings.getString( "quote", "looks like you have no quote to live for!" );
        Logging.print( "quote: " + quote );
    }
}

