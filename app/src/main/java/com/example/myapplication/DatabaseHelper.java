package com.example.myapplication;

import static com.example.myapplication.data.ItemContract.ItemEntry.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "items.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_ITEMS = "items";

    // Column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ROD_DIAMETER = "rod_diameter";
    public static final String COLUMN_UNIT_WEIGHT = "unit_weight";
    public static final String COLUMN_UNIT_PRICE = "unit_price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOTAL = "total";

    // Create table query
    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ITEM_NAME + " TEXT, " +
            COLUMN_ROD_DIAMETER + " REAL, " +
            COLUMN_UNIT_WEIGHT + " REAL, " +
            COLUMN_UNIT_PRICE + " REAL, " +
            COLUMN_QUANTITY + " INTEGER, " +
            COLUMN_TOTAL + " REAL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    public void truncateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ITEMS);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
}
