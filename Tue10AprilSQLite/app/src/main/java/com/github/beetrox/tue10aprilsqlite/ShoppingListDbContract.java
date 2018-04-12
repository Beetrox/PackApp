package com.github.beetrox.tue10aprilsqlite;

import android.provider.BaseColumns;

import static android.os.Build.ID;

public class ShoppingListDbContract {

    private ShoppingListDbContract() {}

    public static class ShoppingItemEntry implements BaseColumns {

        public static final String TABLE_NAME = "shopping_item";
        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_ITEM_COUNT = "item_count";

        public static final String SQL_CREATE_TABLE =
                "CREATE_TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_ITEM_NAME + " TEXT UNIQUE NOT NULL, " +
                    COLUMN_ITEM_COUNT + " INTEGER NOT NULL)";
    }



}
