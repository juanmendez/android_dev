package info.juanmendez.android.preferencefragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Date;

import static info.juanmendez.android.preferencefragment.DatePreference.SUMMARY_FORMATTER;


/**
 * Created by Juan Mendez on 10/19/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 *
 * This activity only shows the values found in myPrefs.
 * Just to assure myself those values are going to that sharedPreferences object
 */
@EActivity(R.layout.layout_second)
@OptionsMenu(R.menu.menu_main)
public class SecondActivity extends AppCompatActivity {

    @Pref
    MyPrefs_ myPrefs;

    @ViewById
    CheckBox prefCheckbox;

    @ViewById
    TextView quoteTextView;

    @ViewById
    TextView dobTextView;

    @ViewById
    TextView favLanguageTextView;

    @AfterViews
    void afterViews(){
        setTitle( "Preferences saved");
        prefCheckbox.setChecked( myPrefs.check_preference().getOr(false) );
        quoteTextView.setText( "favorite quote: " + myPrefs.quote().getOr("no quote :("));

        String dobString = myPrefs.dob().get();
        Date dob = DatePreference.stringToDate( dobString );
        dobString = SUMMARY_FORMATTER.format(dob.getTime());

        dobTextView.setText( "date saved: " + dobString );
        favLanguageTextView.setText( "favorite language: " + myPrefs.fav_language().getOr("no language given"));
    }

    //avoid menu_item which points to this activity
    @InjectMenu
    void setMenu(Menu menu_main){
        menu_main.removeItem(R.id.action_second_activity);
    }

    @OptionsItem(R.id.action_settings)
    void onSettingActivity(){
        Intent intent = new Intent( this, MainActivity_.class );
        startActivity( intent );
    }
}
