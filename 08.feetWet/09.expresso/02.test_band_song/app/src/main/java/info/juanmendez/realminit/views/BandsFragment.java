package info.juanmendez.realminit.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import info.juanmendez.realminit.R;
import info.juanmendez.realminit.RealmApplication;
import info.juanmendez.realminit.adapters.band.BandAdapter;
import info.juanmendez.realminit.models.Band;
import io.realm.Realm;

/**
 * Created by Juan Mendez on 9/11/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
@EFragment(R.layout.fragment_songs)
public class BandsFragment extends Fragment{

    @ViewById(R.id.songListView)
    RealmRecyclerView listView;

    @ViewById(R.id.fragmentToolbar)
    Toolbar toolBar;

    @Inject
    Realm realm;

    @ViewById(R.id.right_pane)
    FrameLayout rightPane;

    BandAdapter adapter;

    @AfterViews
    void afterViews(){
        setRetainInstance(true);
        RealmApplication.inject( this );
        configRecyclerView();
        toolBar.setTitle("List of bands");
    }

    public static Fragment create() {
        BandsFragment fragmment = BandsFragment_.builder().build();
        return fragmment;
    }

    public void configRecyclerView(){

        if( adapter == null ){
            adapter = new BandAdapter( getContext(), realm.where( Band.class ).findAll(), true, true, null);
        }
        listView.setAdapter( adapter );
    }

    @Click( R.id.button_add_song )
    public void addBandHandler(){
        FragmentManager fm = getFragmentManager();
        BandFormDialog bandFormDialog = new BandFormDialog();
        bandFormDialog.show( fm, "band_form" );
    }
}
