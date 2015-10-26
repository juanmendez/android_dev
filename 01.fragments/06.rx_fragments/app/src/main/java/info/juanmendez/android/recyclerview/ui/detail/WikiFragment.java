package info.juanmendez.android.recyclerview.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Juan on 10/24/2015.
 */
public class WikiFragment extends WebViewFragment
{
    public static WikiFragment create( String url ){
        WikiFragment wf = new WikiFragment();
        Bundle args = new Bundle();
        args.putString("url", url );
        wf.setArguments( args );

        return wf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WebView webView = (WebView) super.onCreateView(inflater, container, savedInstanceState);

        if( !rotated() ){
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });

            if( getBundle() != null )
            {
                String url = getBundle().getString( "url", "" );

                if( !url.isEmpty() )
                {
                    webView.loadUrl( url );
                }
            }
        }

        return webView;
    }

    private Bundle getBundle(){

        if( getArguments() != null )
            return getArguments();

        if( getActivity() != null && getActivity().getIntent() != null )
            getActivity().getIntent().getExtras();

        return null;
    }
}