package info.juanmendez.android.intentservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.helper.DownloadProxy;
import info.juanmendez.android.intentservice.model.Magazine;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Inject
    DownloadProxy receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        ((MagazineApp) getApplication()).inject(this);

        getLatestMagazine();
    }

    private void getLatestMagazine(){
        receiver.startService(this, new DownloadProxy.UiCallback() {
            @Override
            public void onReceiveResult(int resultCode, Magazine magazine) {
                if( magazine != null )
                    webView.loadUrl( magazine.getLocation() + "/index.html");
            }
        });
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