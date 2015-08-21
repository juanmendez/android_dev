package info.juanmendez.android.androidconnectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Connectivity extends AppCompatActivity {

    ConnectivityManager cm;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectivity_layout);


        textView = (TextView) findViewById(R.id.textView);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        getDataSettings();
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

        NetworkInfo info = cm.getActiveNetworkInfo();

        if( info != null ){
            boolean isConnected =  info.isConnectedOrConnecting();
            textView.setText( "connected " + ( isConnected?"yes":"no" ));
            textView.append( "\ntype: " + info.getTypeName());
        }else{
            textView.setText( "seems like there is no network available  ");
        }
    }
}
