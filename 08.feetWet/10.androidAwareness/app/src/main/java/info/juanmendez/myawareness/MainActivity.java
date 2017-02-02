package info.juanmendez.myawareness;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tbruyelle.rxpermissions.RxPermissions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    /*@Inject
    AwarenessClient awarenessClient;*/

    @ViewById
    Button btn_permission;


    RxPermissions rxPermissions;
    static final String TAG = Class.class.getSimpleName();

    @AfterViews
    public void afterViews() {

        /*App.getGraph().inject( this );

        awarenessClient.connect();

        rxPermissions = new RxPermissions(this);

        RxView.clicks( btn_permission ).compose( rxPermissions.ensure(Manifest.permission.CAMERA ) )
                .subscribe(aBoolean -> {
                    Log.i( aBoolean?"granted":"!granted", TAG );
                });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //awarenessClient.disconnect();
    }
}