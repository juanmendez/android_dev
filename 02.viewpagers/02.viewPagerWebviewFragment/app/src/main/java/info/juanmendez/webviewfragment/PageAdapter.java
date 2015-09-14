package info.juanmendez.webviewfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Juan on 9/13/2015.
 */
public class PageAdapter extends FragmentPagerAdapter
{

    ArrayList<Page> pages = new ArrayList<Page>();

    public PageAdapter(FragmentManager fm ){
        super( fm);
    }

    public void addPage(String url, String title){
        pages.add(Page.createPage(url, title));
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle( int position ){
        return pages.get(position).getTitle();
    }
}
