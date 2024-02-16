package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.DataTruncation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private DbHelper dbHelper;
    private Context context;

    private SQLiteDatabase sqLiteDatabase;

    public DbManager(Context c) {
        this.context =c;
    }

    public DbManager open() throws SQLException{
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        dbHelper.close();
    }

    public void insert(String itemName, String rodDiameter, String unitWeight, String unitPrice, String quantity, String total){
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.ITEM_NAME, itemName);
        contentValue.put(dbHelper.ROD_DIAMETER, rodDiameter);
        contentValue.put(dbHelper.UNIT_WEIGHT, unitWeight);
        contentValue.put(dbHelper.UNIT_PRICE, unitPrice);
        contentValue.put(dbHelper.QUANTITY, quantity);
        contentValue.put(dbHelper.TOTAL, total);
    }
    public List<String[]> fetch(){
        List<String[]> items = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.query("Items", new String[]{"item_name", "rod_diameter", "unit_weight", "unit_price", "quantity", "total"}, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("item_name"));
                    @SuppressLint("Range") String rodDiameter = cursor.getString(cursor.getColumnIndex("rod_diameter"));
                    @SuppressLint("Range") String unitWeight = cursor.getString(cursor.getColumnIndex("item_name"));
                    @SuppressLint("Range") String unitPrice = cursor.getString(cursor.getColumnIndex("unit_price"));
                    @SuppressLint("Range") String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
                    String[] item = {name, rodDiameter, unitWeight, unitPrice, quantity};
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return items;
    }
    public void delete(String _id){
        sqLiteDatabase.delete(DbHelper.TABLE_NAME, DbHelper._ID + "=" + _id, null);

    }
}
