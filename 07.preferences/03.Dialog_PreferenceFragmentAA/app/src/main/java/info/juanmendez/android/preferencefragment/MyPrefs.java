package info.juanmendez.android.preferencefragment;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Juan Mendez on 10/19/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface MyPrefs {

    boolean check_preference();
    String fav_language();
    String quote();

    @DefaultString(value="1991.01.01")
    String dob();
}
