package com.example.sqlbrite.todo.db;

import android.content.ContentValues;
import android.database.Cursor;

import auto.parcel.AutoParcel;
import rx.functions.Func1;

/**
 * Created by Juan on 12/26/2015.
 */
@AutoParcel
public abstract class TodoItem {

    /**
     * table name, and column names
     */
    public static final String TABLE = "todo_item";
    public static final String ID = "_id";
    public static final String LIST_ID = "todo_list_id";
    public static final String DESCRIPTION = "description";
    public static final String COMPLETE = "complete";

    /**
     * autoparcel members
     */
    public abstract long id();
    public abstract long listId();
    public abstract String description();
    public abstract boolean complete();


    public static final Func1<Cursor, TodoItem> MAPPER = cursor -> {
        long id = Db.getLong(cursor, ID);
        long listId = Db.getLong(cursor, LIST_ID);
        String description = Db.getString(cursor, DESCRIPTION);
        boolean complete = Db.getBoolean(cursor, COMPLETE);
        return new AutoParcel_TodoItem(id, listId, description, complete);
    };

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder listId(long listId) {
            values.put(LIST_ID, listId);
            return this;
        }

        public Builder description(String description) {
            values.put(DESCRIPTION, description);
            return this;
        }

        public Builder complete(boolean complete) {
            values.put(COMPLETE, complete);
            return this;
        }

        public ContentValues build() {
            return values; // TODO defensive copy?
        }
    }
}
