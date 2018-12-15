package info.juanmendez.android.intentservice.ui.listmagazine;

import android.app.LoaderManager;
import android.widget.ListView;

import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.service.network.NetworkUpdate;

/**
 * Created by Juan on 8/19/2015.
 */
public interface IListMagazinesView extends NetworkUpdate{
    void onMagazineList();
    void setAdapter( MagazineAdapter adapter );
}
