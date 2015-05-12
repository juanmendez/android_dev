package info.juanmendez.android.asynctask00;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity implements Runnable {

    Boolean isRunning = false;

    @ViewById
    TextView textView;

    View root;
    private static final int PERIOD=5000;

    private int times = 0;

    @AfterViews
    void afterViews()
    {
        //? http://stackoverflow.com/questions/7776768/android-what-is-android-r-id-content-used-for
        root = findViewById( android.R.id.content );
    }

    @Override
    public void onResume(){
        super.onResume();
        doRun(true);
    }


    @Override
    public void onPause(){

        doRun( false );
        super.onPause();
    }


    @Override
    public void run(){
        times++;
        Toast.makeText(  MainActivity.this, "Who-hoo!", Toast.LENGTH_SHORT ).show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText( "running " + times );
            }
        });

        root.postDelayed( this, PERIOD );
    }

    @Click(R.id.startButton)
    void startRunnable(){
        doRun( true );
    }

    @Click(R.id.stopButton)
    void stopRunnable(){
        doRun( false );
    }

    void doRun( Boolean start )
    {
        //without this condition, the loop is adding several postDelays...
        if( isRunning != start )
        {
            if( start == true )
            {
                run();
                isRunning = true;
            }
            else
            {
                root.removeCallbacks(this);
                times = 0;
                isRunning = false;
            }
        }
    }

}
