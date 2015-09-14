package info.juanmendez.webviewfragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * WebViewFragment support for android.support.v4.app.Fragment
 * This demo has been tested for SDK 10 and 22.
 *
 * @Author @juanmendezinfo
 * @see <a href="http://www.juanmendez.info/2015/09/webviewfragment-which-supports.html">details</a>
 */
public class WebViewFragment extends Fragment
{
    private WebView mWebView;
    private boolean mIsWebViewAvailable;
    private boolean mRotated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);

        if( mWebView == null  )
        {
            mWebView = new WebView(getActivity());
        }

        return mWebView;
    }


    /**
     * let us know if the webView has been rotated.
     * @return
     */
    public boolean rotated() {
        return mRotated;
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();

        if (honeyOrHigher())
            mWebView.onPause();

        mRotated = true;
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {

        if (honeyOrHigher())
            mWebView.onResume();

        super.onResume();
    }

    private boolean honeyOrHigher(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        mIsWebViewAvailable = false;

        if( mWebView != null )
        {
            ViewGroup parentViewGroup = (ViewGroup) mWebView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViewsInLayout();;
            }
        }

        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * Gets the WebView.
     */
    public WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }
}
