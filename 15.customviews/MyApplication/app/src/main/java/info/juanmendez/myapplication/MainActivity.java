package info.juanmendez.myapplication;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button mButton = findViewById(R.id.thisButton);
        new ButtonTask(mButton).execute();

    }


    class ButtonTask extends AsyncTask<Void, Void, String>{

        Button mButton;

        public ButtonTask(@NonNull  Button button) {
            mButton = button;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                Thread.sleep( 500 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Hello Button";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mButton.setText( s );
        }
    }
}
