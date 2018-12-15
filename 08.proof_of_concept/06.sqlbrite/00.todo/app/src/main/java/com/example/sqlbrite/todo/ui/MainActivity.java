package com.example.sqlbrite.todo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sqlbrite.todo.R;
import com.example.sqlbrite.todo.ui.items.ItemsFragment;
import com.example.sqlbrite.todo.ui.items.NewItemFragment;
import com.example.sqlbrite.todo.ui.lists.ListsFragment;
import com.example.sqlbrite.todo.ui.lists.NewListFragment;

public class MainActivity extends AppCompatActivity implements ListsFragment.Listener, ItemsFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add( android.R.id.content, ListsFragment.newInstance()).commit();

        }
    }

    @Override public void onListClicked(long id) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(android.R.id.content, ItemsFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }

    @Override public void onNewListClicked() {
        NewListFragment.newInstance().show(getSupportFragmentManager(), "new-list");
    }

    @Override public void onNewItemClicked(long listId) {
        NewItemFragment.newInstance(listId).show(getSupportFragmentManager(), "new-item");
    }
}
