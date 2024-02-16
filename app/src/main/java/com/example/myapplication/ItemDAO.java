package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private DatabaseHelper dbHelper;
    private Context context;

    public ItemDAO(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public long insertItem(Item item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ITEM_NAME, item.getItemName());
        values.put(DatabaseHelper.COLUMN_ROD_DIAMETER, item.getRodDiameter());
        values.put(DatabaseHelper.COLUMN_UNIT_WEIGHT, item.getUnitWeight());
        values.put(DatabaseHelper.COLUMN_UNIT_PRICE, item.getUnitPrice());
        values.put(DatabaseHelper.COLUMN_QUANTITY, item.getQuantity());
        values.put(DatabaseHelper.COLUMN_TOTAL, item.getTotal());

        long id = db.insert(DatabaseHelper.TABLE_ITEMS, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_ITEMS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_NAME)));
                item.setRodDiameter(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROD_DIAMETER)));
                item.setUnitWeight(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIT_WEIGHT)));
                item.setUnitPrice(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIT_PRICE)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY)));
                item.setTotal(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_TOTAL)));
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return items;
    }
}
