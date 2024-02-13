package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DbHelper d = new DbHelper(this);
        Log.i("DbHelper", d.CREATE_TABLE_STMT);
    }
    public void onClickPurchase(View view) {
        startActivity(new Intent(HomeActivity.this,PurchaseActivity.class));
    }

    public void onClickRawMaterialEntry(View view) {
        startActivity(new Intent(HomeActivity.this,RawMaterialEntryActivity.class));
    }

    public void onClickNewOrder(View view) {
        startActivity(new Intent(HomeActivity.this,CatalogActivity.class));
    }
}