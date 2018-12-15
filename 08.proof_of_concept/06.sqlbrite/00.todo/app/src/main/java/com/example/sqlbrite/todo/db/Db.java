package com.example.sqlbrite.todo.db;

import android.database.Cursor;

/**
 * Created by Juan on 12/26/2015.
 */
public class Db {
    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    /**
     * gets string value by columnName
     * @param cursor
     * @param columnName
     * @return
     */
    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    /**
     * gets boolean by column name
     * @param cursor
     * @param columnName
     * @return
     */
    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    /**
     * gets long by column name
     * @param cursor
     * @param columnName
     * @return
     */
    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    /**
     * gets integer by column name
     * @param cursor
     * @param columnName
     * @return
     */
    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    /**
     * ensure there is no instance made
     */
    private Db() {
        throw new AssertionError("No instances.");
    }
}
