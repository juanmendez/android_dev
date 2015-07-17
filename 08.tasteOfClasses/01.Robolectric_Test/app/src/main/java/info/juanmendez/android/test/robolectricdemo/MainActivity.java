package info.juanmendez.android.test.robolectricdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button clickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickButton = (Button) findViewById(R.id.clickButton);
        clickButton.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent( this, SecondActivity.class );
        startActivity( intent );
    }
}
