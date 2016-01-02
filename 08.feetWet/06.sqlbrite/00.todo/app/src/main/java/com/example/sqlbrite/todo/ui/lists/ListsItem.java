package com.example.sqlbrite.todo.ui.lists;

import android.database.Cursor;

import com.example.sqlbrite.todo.db.Db;
import com.example.sqlbrite.todo.db.TodoItem;
import com.example.sqlbrite.todo.db.TodoList;

import java.util.Arrays;
import java.util.Collection;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

/**
 * Created by Juan on 12/28/2015.
 */

@AutoParcel
public abstract class ListsItem {
    private static String ALIAS_LIST = "list";
    private static String ALIAS_ITEM = "item";

    private static String LIST_ID = ALIAS_LIST + "." + TodoList.ID;
    private static String LIST_NAME = ALIAS_LIST + "." + TodoList.NAME;
    private static String ITEM_COUNT = "item_count";
    private static String ITEM_ID = ALIAS_ITEM + "." + TodoItem.ID;
    private static String ITEM_LIST_ID = ALIAS_ITEM + "." + TodoItem.LIST_ID;

    public static Collection<String> TABLES = Arrays.asList(TodoList.TABLE, TodoItem.TABLE);
    public static String QUERY = ""
            + "SELECT " + LIST_ID + ", " + LIST_NAME + ", COUNT(" + ITEM_ID + ") as " + ITEM_COUNT
            + " FROM " + TodoList.TABLE + " AS " + ALIAS_LIST
            + " LEFT OUTER JOIN " + TodoItem.TABLE + " AS " + ALIAS_ITEM + " ON " + LIST_ID + " = " + ITEM_LIST_ID
            + " GROUP BY " + LIST_ID;

    public abstract long id();
    public abstract String name();
    public abstract int itemCount();

    public static Func1<Cursor, ListsItem> MAPPER = cursor -> {
        long id = Db.getLong(cursor, TodoList.ID);
        String name = Db.getString(cursor, TodoList.NAME);
        int itemCount = Db.getInt(cursor, ITEM_COUNT);
        return new AutoParcel_ListsItem(id, name, itemCount);
    };
}
