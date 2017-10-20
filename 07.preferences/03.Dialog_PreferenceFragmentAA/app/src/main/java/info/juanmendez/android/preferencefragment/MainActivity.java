package info.juanmendez.android.preferencefragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.res.BooleanRes;

@EActivity(R.layout.activity_show)
@OptionsMenu(R.menu.menu_main)
public class MainActivity  extends AppCompatActivity {

    @BooleanRes
    boolean doublePane;

    @AfterViews
    void afterViews(){
        /*FragmentManager fm = getFragmentManager();

        if( doublePane ){
            if( fm.findFragmentByTag("dialogPreference") == null ){
                DialogPreference dialogPreference = DialogPreference_.builder().build();
                dialogPreference.show( fm, "dialogPreference");
            }

            if( fm.findFragmentByTag("prefFragment") != null ){
                fm.beginTransaction().remove( fm.findFragmentByTag("prefFragment") ).commit();
            }
        }else{
            if( fm.findFragmentByTag("prefFragment") == null ){
                FragmentPref fragmentPref = FragmentPref_.builder().build();
                fm.beginTransaction().add( R.id.main_container, fragmentPref, "prefFragment").commit();
            }

            if( fm.findFragmentByTag("dialogPreference") != null ){
                fm.beginTransaction().remove( fm.findFragmentByTag("dialogPreference") ).commit();
            }
        }*/
    }

    //avoid menu_item which points to this activity
    @InjectMenu
    void setMenu(Menu menu_main){
        menu_main.removeItem(R.id.action_settings);
    }

    @OptionsItem(R.id.action_second_activity)
    void onSecondActivity(){
        Intent intent = new Intent( this, SecondActivity_.class );
        startActivity( intent );
    }
}