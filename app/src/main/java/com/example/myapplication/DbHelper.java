package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "stocks";
    public static final String TABLE_NAME = "Items";
    public static final String _ID = "_id";
    public static final String ITEM_NAME = "item_name";
    public static final String ROD_DIAMETER = "rod_diameter";
    public static final String UNIT_WEIGHT = "unit_weight";
    public static final String UNIT_PRICE = "unit_price";
    public static final String QUANTITY = "quantity";

    public static final String TOTAL = "total";

    public static final int DB_VERSION = 1;
    public static final String CREATE_TABLE_STMT = "create table " + DB_NAME.toString() + "." + TABLE_NAME.toString() +
            "(" + "_id int primary key not null, " + ITEM_NAME + " text not null, " + ROD_DIAMETER + " text not null, " +
            UNIT_WEIGHT + " text not null, " + UNIT_PRICE + " text not null, " + QUANTITY + " text null, " +
            TOTAL + " text noll null ); ";
    public DbHelper(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(CREATE_TABLE_STMT);
        Log.i("DbHelper",CREATE_TABLE_STMT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
//        sqLiteDatabase.execSQL(CREATE_TABLE_STMT);
    }
}
