package info.juanmendez.timber;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hahaha, I thought I needed a tag
        //Timber.i( MainActivity.class.getSimpleName(), "hello" );

        Timber.i( "hello" );
        Timber.d( "hello" );
        Timber.v( "hello" );
    }
}
