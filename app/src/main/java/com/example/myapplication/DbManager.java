package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.DataTruncation;
import java.sql.SQLException;

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
    public Cursor fetch(){
        String[] columns = new String[]{DbHelper._ID, DbHelper.ITEM_NAME, DbHelper.ROD_DIAMETER, DbHelper.UNIT_WEIGHT, DbHelper.UNIT_PRICE, DbHelper.QUANTITY, DbHelper.TOTAL};
        Cursor cursor = sqLiteDatabase.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public void delete(String _id){
        sqLiteDatabase.delete(DbHelper.TABLE_NAME, DbHelper._ID + "=" + _id, null);

    }
}
