package info.juanmendez.android.preferencefragment;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_show)
@OptionsMenu(R.menu.menu_main)
public class MainActivity  extends AppCompatActivity {

    @Click
    void openPreference(){

        FragmentManager fm = getSupportFragmentManager();

        DialogPreference dialogPreference = DialogPreference_.builder().build();
        dialogPreference.show( fm, "dialogPreference");
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