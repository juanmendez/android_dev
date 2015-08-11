package info.juanmendez.android.androidconnectivity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.viralpatel.network.NetworkUtil;

public class Connectivity extends AppCompatActivity {

    ConnectivityManager cm;
    TextView textView;
    NetworkChangeReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectivity_layout);


        textView = (TextView) findViewById(R.id.textView);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        getDataSettings();
    }

    @Override
    public void onResume(){
        super.onResume();

        if( receiver == null )
            receiver = new NetworkChangeReceiver();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver( receiver, filter );
    }

    @Override
    public void onPause(){
        super.onPause();

        if( receiver != null )
            unregisterReceiver( receiver );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_connectivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_network) {
            getDataSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDataSettings(){

        Boolean connected = NetworkUtil.isConnected(this);
        if( connected ){

            textView.setText( "connected " + ( connected? "yes":"no" ));
            textView.append( "\ntype: " + NetworkUtil.getConnectivityStatusString(this));
        }else{
            textView.setText( "seems like there is no network available  ");
        }
    }
}
