package info.juanmendez.android.preferencefragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceClick;
import org.androidannotations.annotations.PreferenceScreen;

import timber.log.Timber;

/**
 * Created by Juan on 6/17/2015.
 */
@PreferenceScreen(R.xml.pref_screen)
@EFragment
public class FragmentPref extends PreferenceFragment {

    @PreferenceByKey(R.string.check_preference)
    CheckBoxPreference checkBoxPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //we have MyPres which declares our preferences so this is how we provide
        //PreferenceFragment our SharedPreferences instance
        getPreferenceManager().setSharedPreferencesName("MyPrefs");
    }

    //similar to @AfterViews in AndroidAnnotations
    // which means we now can have direct access to preferences like checkBoxPref above
    @AfterPreferences
    void initPrefs() {
    }

    //changes made to fav_language field
    @PreferenceChange({R.string.fav_language})
    void onFavLanguage(Preference preference, String newValue) {
        Timber.i( "quote changed to %s", newValue);
    }

    //changes made to quote field
    @PreferenceChange({R.string.quote})
    void onChangeQuote(Preference preference, String newValue) {
        Timber.i( "quote changed to %s", newValue);
    }

    //changes made to dob field
    //this is not being executed, as this is a custom preference.
    //hopefully I figure this out at a later time.
    @PreferenceChange({R.string.dob})
    void onDOBChange(Preference preference, String newValue) {
        Timber.i( "dob changed to %s", newValue);
    }

    //changes found when clicking on check_preference field.
    @PreferenceClick(R.string.check_preference)
    void clickOnMyPref(Preference preference ) {
        Timber.i( "first checkbox was clicked" );
    }
}