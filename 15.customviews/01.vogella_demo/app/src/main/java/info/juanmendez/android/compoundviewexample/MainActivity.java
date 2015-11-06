package info.juanmendez.android.compoundviewexample;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    public void onClicked( View view) {
        String text = view.getId() == R.id.view1 ? "Background" : "Foreground";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
