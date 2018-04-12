package com.github.beetrox.tue10aprilsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ShoppingListDbHelper dbHelper;
    List<ShoppingItem> shoppingList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ShoppingListDbHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        addItemToDatabase(new ShoppingItem("majs", 5), db);

        readItemsFromDatabase(db);

        for(ShoppingItem item : shoppingList) {
            Log.d("Cleo", "onCreate", item.name);
        }

        String[] itemColumns = {
                ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_NAME,
                ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_COUNT
        };

        Cursor cursor = db.query(ShoppingListDbContract.ShoppingItemEntry.TABLE_NAME, itemColumns, null, null, null, null, ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_NAME + " DESC");

        int itemNamePosition = cursor.getColumnIndex(ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_NAME);
        int itemCountPosition = cursor.getColumnIndex(ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_COUNT);

        List itemNames = new ArrayList<String>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(itemNamePosition);
            itemNames.add(name);
        }
        cursor.close();

        readFromDatabase(db);

        Log.d("Cleo", "onCreate" + shoppingList.get(0).name);
    }

    void addItemToDatabase(ShoppingItem item, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_NAME, item.name);
        values.put(ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_COUNT, item.count);

        db.insert(ShoppingListDbContract.ShoppingItemEntry.TABLE_NAME, null, values);

    }

    void readFromDatabase(SQLiteDatabase db) {

        String[] itemColumns = {
                ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_NAME,
                ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_COUNT
        };

        Cursor cursor = db.query(ShoppingListDbContract.ShoppingItemEntry.TABLE_NAME, itemColumns, null, null, null, null, ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_NAME + " DESC");

        int itemNamePosition = cursor.getColumnIndex(ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_NAME);
        int itemCountPosition = cursor.getColumnIndex(ShoppingListDbContract.ShoppingItemEntry.COLUMN_ITEM_COUNT);

        List itemNames = new ArrayList<String>();

        shoppingList.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(itemNamePosition);
            int count = cursor.getInt(itemCountPosition);
            itemNames.add(name);

            ShoppingItem item = new ShoppingItem(name, count);
            shoppingList.add(item);
        }
        cursor.close();

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
