package info.juanmendez.android.intentservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import info.juanmendez.android.intentservice.helper.Logging;
import info.juanmendez.android.intentservice.model.Version;
import info.juanmendez.android.intentservice.model.VersionService;
import info.juanmendez.android.intentservice.service.downloading.DownloadReceiver;
import info.juanmendez.android.intentservice.service.downloading.DownloadService;
import info.juanmendez.android.intentservice.service.versioning.RetroService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        DownloadReceiver downloadReceiver = new DownloadReceiver( new Handler() );

        downloadReceiver.setCallback(new DownloadReceiver.Callback() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == Activity.RESULT_OK) {
                    webView.loadUrl("file://" + resultData.getString("directory") + "/index.html");
                }
            }
        });

        Intent i = new Intent( this, DownloadService.class );
        i.putExtra("receiver", downloadReceiver);
        i.putExtra( "zipUrl", "http://ketchup/development/android/magazine/mag_0.1/www.zip" );
        i.putExtra( "version", 0.1f );
        startService( i );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}