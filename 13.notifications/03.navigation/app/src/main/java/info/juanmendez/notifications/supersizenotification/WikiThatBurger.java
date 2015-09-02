package info.juanmendez.notifications.supersizenotification;

import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Juan on 9/2/2015.
 */
public class WikiThatBurger extends AppCompatActivity
{
    WebView webView;
    NotificationManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl( "https://en.wikipedia.org/wiki/Whopper" );
        tellMeAboutIt(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent){
        tellMeAboutIt(intent);
    }

    private void tellMeAboutIt( Intent intent ){
        mgr.cancel(SuperSizeActivity.id);
        if( intent.getStringExtra("meal") != null ){

            Toast.makeText(this, intent.getStringExtra("meal"), Toast.LENGTH_LONG).show();
        }
    }
}
