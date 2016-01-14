package info.juanmendez.android.intentservice.model.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.helper.MVPUtils;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.pojo.Page;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;
import info.juanmendez.android.intentservice.ui.MagazinePage;
import info.juanmendez.android.intentservice.ui.magazine.IMagazineView;
import rx.functions.Action1;

/**
 * Created by Juan on 8/8/2015.
 */
public class WebViewAdapter extends FragmentPagerAdapter implements Action1<List<MagazinePage>> {

    @Inject
    ArrayList<MagazinePage> magazinePages;
    AppCompatActivity activity;

    @Inject
    MagazineDispatcher dispatcher;

    public WebViewAdapter( AppCompatActivity activity) {
        super(activity.getSupportFragmentManager());

        this.activity = activity;
        MVPUtils.getView(activity, IMagazineView.class).inject(this);
    }

    @Override
    public int getCount() {
        return magazinePages.size();
    }

    @Override
    public Fragment getItem(int position) {

        return magazinePages.get(position);
    }

    @Override
    public void call(List<MagazinePage> incomingPages ) {
        magazinePages.clear();
        magazinePages.addAll( incomingPages );
        notifyDataSetChanged();
    }
}
