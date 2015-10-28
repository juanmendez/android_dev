package info.juanmendez.android.recyclerview.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import info.juanmendez.android.recyclerview.App;
import info.juanmendez.android.recyclerview.R;
import info.juanmendez.android.recyclerview.rx.UIObservable;
import rx.Subscription;

/**
 * Created by Juan on 10/24/2015.
 */
public class WikiFragment extends WebViewFragment
{

    Boolean isInSecondPane = false;
    Subscription subscription;
    WebView webView;

    public static WikiFragment create( String url ){
        WikiFragment wf = new WikiFragment();
        Bundle args = new Bundle();
        args.putString("url", url );
        wf.setArguments(args);

        return wf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webView = (WebView) super.onCreateView(inflater, container, savedInstanceState);

        isInSecondPane = getResources().getBoolean(R.bool.dual_fragments );


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

    @Override
    public void onStart() {
        super.onStart();

        subscription = getObservable().subscribe(country -> {
            webView.loadUrl(country.getLink());
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        if( subscription != null )
        {
            getObservable().unsubscribe( subscription );
            subscription = null;
        }
    }

    private Bundle getBundle(){

        if( getArguments() != null )
            return getArguments();

        if( getActivity() != null && getActivity().getIntent() != null )
            return getActivity().getIntent().getExtras();

        return null;
    }

    private UIObservable getObservable(){

        return ((App) getActivity().getApplication()).getObservable();
    }
}