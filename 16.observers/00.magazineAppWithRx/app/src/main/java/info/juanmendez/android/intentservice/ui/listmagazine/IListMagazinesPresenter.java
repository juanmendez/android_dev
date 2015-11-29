package info.juanmendez.android.intentservice.ui.listmagazine;

import android.app.LoaderManager;

import java.util.ArrayList;

import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.service.network.NetworkUpdate;
import info.juanmendez.android.intentservice.service.proxy.DownloadProxy;
import info.juanmendez.android.intentservice.service.proxy.MagazineListProxy;

/**
 * Created by Juan on 8/19/2015.
 */
public interface IListMagazinesPresenter extends NetworkUpdate, DownloadProxy.UiCallback, LoaderManager.LoaderCallbacks<ArrayList<Magazine>> {

    void pause();
    void resume();
    void loadMagazine();
    void refreshList(Boolean forceQuery);
}
