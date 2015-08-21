package info.juanmendez.android.intentservice.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import dagger.ObjectGraph;
import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.helper.NetworkUtil;
import info.juanmendez.android.intentservice.model.adapter.MagazineAdapter;
import info.juanmendez.android.intentservice.module.ActivityModule;
import info.juanmendez.android.intentservice.ui.listmagazine.IListMagazinesPresenter;
import info.juanmendez.android.intentservice.ui.listmagazine.IListMagazinesView;
import info.juanmendez.android.intentservice.ui.listmagazine.ListMagazinesPresenter;

/**
 * Created by Juan on 7/29/2015.
 */
public class ListMagazinesActivity extends AppCompatActivity  implements IListMagazinesView {

    ObjectGraph graph;
    ListView list;
    MagazineApp app;
    Button noNetworkButton;
    IListMagazinesPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        noNetworkButton = (Button) findViewById(R.id.noNetworkButton );

        noNetworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isConnected(ListMagazinesActivity.this)) {
                    presenter.getMagazines();
                }
            }
        });

        list = (ListView) findViewById(R.id.list );
        presenter = new ListMagazinesPresenter(this);

        app = (MagazineApp)getApplication();
        graph = app.getGraph().plus( new ActivityModule(this));
        graph.inject(presenter);
    }

    @Override
    public void onMagazineList() {
        noNetworkButton.setVisibility(View.GONE);
    }

    @Override
    public void setAdapter(MagazineAdapter adapter) {
        list.setAdapter( adapter );
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause(){
        presenter.pause();
        super.onPause();
    }

    @Override
    public void onNetworkStatus(Boolean connected, String type) {

        if( connected ){

            if( noNetworkButton.getVisibility() == View.VISIBLE )
            {
                noNetworkButton.setText(getString(R.string.error_network_refresh));
            }
        }
        else{
            noNetworkButton.setText(getString(R.string.error_network));
            noNetworkButton.setVisibility(View.VISIBLE);
        }
    }
}