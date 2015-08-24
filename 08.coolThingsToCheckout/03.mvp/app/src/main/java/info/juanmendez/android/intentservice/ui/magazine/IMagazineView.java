package info.juanmendez.android.intentservice.ui.magazine;

import android.support.v4.app.LoaderManager;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Juan on 8/20/2015.
 */
public interface IMagazineView {

    void inject( Object object );
    void setAdapter( PagerAdapter adapter );
}
