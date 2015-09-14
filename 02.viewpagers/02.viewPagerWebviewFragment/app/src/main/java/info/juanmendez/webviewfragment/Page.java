package info.juanmendez.webviewfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * This is a demo of extending WebViewFragment.
 */
public class Page extends WebViewFragment
{

    public static Page createPage( String url, String title ){
        Bundle bundle = new Bundle();
        bundle.putString("url", url );
        bundle.putString("title", title);

        Page page = new Page();
        page.setArguments(bundle);

        return page;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WebView webView = (WebView) super.onCreateView(inflater, container, savedInstanceState);

        if( !rotated() )
        {
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });

            webView.loadUrl(getUrl());
        }

        return webView;
    }

    public String getUrl(){
        return getArguments().getString("url");
    }

    public String getTitle(){
        return getArguments().getString("title");
    }
}