package info.juanmendez.android.intentservice.ui.magazine;

import android.database.Cursor;
import android.app.LoaderManager;

/**
 * Created by Juan on 8/20/2015.
 */
public interface IMagazinePresenter extends LoaderManager.LoaderCallbacks<Cursor> {

    void pause();
    void resume();
}
