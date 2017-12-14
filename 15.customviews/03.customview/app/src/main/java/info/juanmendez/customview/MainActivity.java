package info.juanmendez.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

//how you inflate an activity
@EActivity(R.layout.expandedbox_layout)
public class MainActivity extends AppCompatActivity {

    //how to reference a ui element in AndroidAnnotations
    @ViewById(R.id.funkyButton)
    Button mButton;

    /**
     *
     * I like using AndroidAnnotations, and I couldn't understand the questions other than provide an Activity class
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}