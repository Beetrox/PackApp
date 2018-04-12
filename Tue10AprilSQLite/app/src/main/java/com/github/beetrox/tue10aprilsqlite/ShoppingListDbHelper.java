package com.github.beetrox.tue10aprilsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShoppingListDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shopping.db";
    public static final int DATABASE_VERSION = 1;

    public ShoppingListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ShoppingListDbContract.ShoppingItemEntry.SQL_CREATE_TABLE);

        // write test entries
        ContentValues values = new ContentValues();
        values.put(ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_NAME, "gurka");
        values.put(ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_COUNT, 4);

        sqLiteDatabase.insert(ShoppingListDbContract.ShoppingItemEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
