package com.example.sqlbrite.todo.ui.lists;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sqlbrite.todo.R;
import com.example.sqlbrite.todo.TodoApp;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Juan on 12/28/2015.
 */
public class ListsFragment extends Fragment {
    public interface Listener {
        void onListClicked(long id);
        void onNewListClicked();
    }

    public static ListsFragment newInstance() {
        return new ListsFragment();
    }

    @Inject
    BriteDatabase db;

    @InjectView(android.R.id.list)
    ListView listView;
    @InjectView(android.R.id.empty)
    View emptyView;

    private Listener listener;
    private ListsAdapter adapter;
    private Subscription subscription;


    @Override public void onAttach(Context context) {
        if (!(context instanceof Listener)) {
            throw new IllegalStateException("Activity must implement fragment Listener.");
        }

        super.onAttach(context);
        TodoApp.objectGraph(context).inject(this);
        setHasOptionsMenu(true);

        listener = (Listener) context;
        adapter = new ListsAdapter(context);
    }

    @Override public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ){
        super.onCreateOptionsMenu( menu, inflater );

        MenuItem item = menu.add("New List").setOnMenuItemClickListener( item1 -> {
            listener.onNewListClicked();
            return true;
        });

        MenuItemCompat.setShowAsAction( item, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM| MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT );
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                       @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lists, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
    }

    @OnItemClick(android.R.id.list) void listClicked(long listId) {
        listener.onListClicked(listId);
    }

    @Override public void onResume() {
        super.onResume();

        getActivity().setTitle("To-Do");

        subscription = db.createQuery(ListsItem.TABLES, ListsItem.QUERY)
                .mapToList(ListsItem.MAPPER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
    }

    @Override public void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }
}
