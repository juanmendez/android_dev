package info.juanmendez.android.intentservice.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Juan on 9/13/2015.
 */
public class MagazinePage extends WebViewFragment
{
    public static MagazinePage build( String url )
    {
        MagazinePage page = new MagazinePage();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        page.setArguments(bundle);

        return page;
    }

    public String getUrl(){

        return getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WebView webView = (WebView) super.onCreateView(inflater, container, savedInstanceState );

        if( isWebViewNew() && getUrl() != null )
        {

            webView.getSettings().setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });

            webView.loadUrl(getUrl());
        }

        return webView;
    }
}
