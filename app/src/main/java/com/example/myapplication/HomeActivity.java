package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




    }
    public void onClickPurchase(View view) {
        startActivity(new Intent(HomeActivity.this, CompanyActivity.class));
    }

    public void onClickRawMaterialEntry(View view) {
        startActivity(new Intent(HomeActivity.this,RawMaterialEntryActivity.class));
    }

    public void onClickNewOrder(View view) {
        startActivity(new Intent(HomeActivity.this,CatalogActivity.class));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            // Handle home item click

        } else if (itemId == R.id.nav_settings) {
            // Handle settings item click

        } else if (itemId == R.id.nav_share) {
            // Handle share item click

        } else if (itemId == R.id.nav_about) {
            // Handle about item click

        } else if (itemId == R.id.nav_logout) {
            // Handle logout item click
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();

        }

        // Close the drawer after handling the click
        return true;
    }


}