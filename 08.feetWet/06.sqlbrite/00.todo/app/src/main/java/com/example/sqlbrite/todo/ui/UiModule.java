package com.example.sqlbrite.todo.ui;

import com.example.sqlbrite.todo.ui.items.ItemsFragment;
import com.example.sqlbrite.todo.ui.items.NewItemFragment;
import com.example.sqlbrite.todo.ui.lists.ListsFragment;
import com.example.sqlbrite.todo.ui.lists.NewListFragment;

import dagger.Module;

/**
 * Created by Juan on 12/28/2015.
 */

@Module(
        injects = {
                ItemsFragment.class,
                ListsFragment.class,
                NewItemFragment.class,
                NewListFragment.class
        },
        complete = false,
        library = true
)
public class UiModule {
}
