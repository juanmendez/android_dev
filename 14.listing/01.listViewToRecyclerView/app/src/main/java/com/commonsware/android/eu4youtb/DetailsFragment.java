/***
  Copyright (c) 2008-2015 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain	a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
	
  From _The Busy Coder's Guide to Android Development_
    https://commonsware.com/Android
*/

package com.commonsware.android.eu4youtb;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

public class DetailsFragment extends Fragment implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
  private WebView webView;
  private ImageView flag;
  private Toolbar toolbar;
  private MenuItem navBack;
  private MenuItem navForward;
  private MenuItem navReload;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View result=inflater.inflate(R.layout.details, container, false);

    webView=(WebView)result.findViewById(R.id.webview);
    flag=(ImageView)result.findViewById(R.id.flag);
    toolbar=(Toolbar)result.findViewById(R.id.toolbar);

    toolbar.inflateMenu(R.menu.webview);
    getNavItems(toolbar.getMenu());
    toolbar.setOnMenuItemClickListener(this);

      /**
       * I could have used a boolean resource, but for quick testing, I simply relied on parent activity
       */
      if( getActivity().getClass().getName().indexOf("DetailsActivity") >= 0 ){
          toolbar.setNavigationIcon(R.drawable.ic_launcher);
          toolbar.setNavigationOnClickListener(this);
      }

    return(result);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    webView.setWebViewClient(new URLHandler());
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case R.id.back:
        if (webView.canGoBack()) {
          webView.goBack();
        }
        break;

      case R.id.fwd:
        if (webView.canGoForward()) {
          webView.goForward();
        }
        break;

      case R.id.reload:
        webView.reload();
        break;

      default:
        return(super.onOptionsItemSelected(item));
    }

    return(true);
  }

  public void showCountry(Country c) {
    webView.loadUrl(getActivity().getString(c.url));

    if (flag!=null) {
      flag.setImageResource(c.flag);
    }
  }

  private void getNavItems(Menu menu) {
    navBack=menu.findItem(R.id.back);
    navForward=menu.findItem(R.id.fwd);
    navReload=menu.findItem(R.id.reload);

    updateNav();
  }

  private void updateNav() {
    navBack.setEnabled(webView.canGoBack());
    navForward.setEnabled(webView.canGoForward());
    navReload.setEnabled(webView.getUrl()!=null);
  }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return(onOptionsItemSelected(item));
    }

    @Override
    public void onClick(View v) {
        getActivity().onBackPressed();
    }

    private class URLHandler extends WebViewClient {
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);

      updateNav();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);

      updateNav();
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
      super.doUpdateVisitedHistory(view, url, isReload);

      updateNav();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      view.loadUrl(url);

      return(true);
    }
  }
}
