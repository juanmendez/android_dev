package info.juanmendez.android.intentservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import javax.inject.Inject;

import dagger.ObjectGraph;
import info.juanmendez.android.intentservice.helper.DownloadProxy;
import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.module.ActivityModule;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    DownloadProxy receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MagazineApp app = (MagazineApp)getApplication();
        ObjectGraph graph = app.getGraph().plus( new ActivityModule(this));
        graph.inject(this);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        receiver = new DownloadProxy(this);
        getLatestMagazine();
    }

    private void getLatestMagazine(){
        receiver.startService( new DownloadProxy.UiCallback() {
            @Override
            public void onReceiveResult(int resultCode) {

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