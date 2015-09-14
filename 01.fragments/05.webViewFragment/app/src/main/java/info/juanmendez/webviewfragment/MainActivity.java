package info.juanmendez.webviewfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fm = getSupportFragmentManager();

        if( fm.findFragmentByTag("clientFragment") == null )
        {
            fm.beginTransaction().add(R.id.frameLayout, new ClientFragment(), "clientFragment").commit();
        }
    }
}
