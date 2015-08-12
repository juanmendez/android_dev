package info.juanmendez.android.intentservice.model.adapter;

import android.app.ActionBar;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.ArrayList;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.MagazineActivity;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.Page;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;

/**
 * Created by Juan on 8/8/2015.
 */
public class WebViewAdapter extends PagerAdapter {

    @Inject
    ArrayList<Page> pages;
    MagazineActivity activity;

    @Inject
    MagazineDispatcher dispatcher;

    public WebViewAdapter( MagazineActivity activity) {
        super();
        activity.inject(this);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Page page = pages.get(position);
        Magazine magazine = dispatcher.getMagazine();

        WebView webView = new WebView( activity );
        ActionBar.LayoutParams params =
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );

        webView.setLayoutParams(params);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl( magazine.getFileLocation() + "/" + page.getName());

        container.addView( webView );

        return webView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView( (WebView) object);
    }
}
