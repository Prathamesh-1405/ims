package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddItemActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    Button addItemBtn;

    ItemDAO itemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        itemDAO = new ItemDAO(this);
        addItemBtn = findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView item = findViewById(R.id.itemField);
                AutoCompleteTextView rodDiameter = findViewById(R.id.rodDiameterField);
                EditText unitWeight = findViewById(R.id.unitWeightField);
                EditText unitPrice = findViewById(R.id.unitPriceField);
                EditText quantity = findViewById(R.id.quantityField);
                EditText total = findViewById(R.id.totalField);

                // getting values

                String itemVal = item.getText().toString();
                String rodDiameterVal = rodDiameter.getText().toString();
                String unitWeightVal = unitWeight.getText().toString();
                String unitPriceVal = unitPrice.getText().toString();
                String quantityVal = quantity.getText().toString();
                String totalVal = total.getText().toString();


                if(!itemVal.isEmpty() && !rodDiameterVal.isEmpty() && !unitWeightVal.isEmpty() && !unitPriceVal.isEmpty() && !quantityVal.isEmpty() && !totalVal.isEmpty()){
                    try {
                        addItem(itemVal, Double.parseDouble(rodDiameterVal), Double.parseDouble(unitWeightVal), Double.parseDouble(unitPriceVal), Integer.parseInt(quantityVal), Double.parseDouble(totalVal));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please data in text fields !", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private void addItem(String itemVal, double rodDiameterVal, double unitWeightVal, double unitPriceVal, int quantiyVal, double totalVal) throws Exception {
//        dbManager.open();
//        dbManager.insert(itemVal, rodDiameterVal, unitWeightVal, unitPriceVal, quantiyVal,totalVal);
//        dbManager.close();
//        Toast.makeText(getApplicationContext(), "Item added successfully !", Toast.LENGTH_SHORT).show();
        Item item = new Item();
        item.setItemName(itemVal);
        item.setRodDiameter(rodDiameterVal);
        item.setUnitWeight(unitWeightVal);
        item.setUnitPrice(unitPriceVal);
        item.setQuantity(quantiyVal);
        item.setTotal(unitPriceVal * quantiyVal);


        long id = itemDAO.insertItem(item);

        if (id != -1){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Item inserted successfully", Toast.LENGTH_SHORT).show();
                }
            });

            startActivity(new Intent(AddItemActivity.this, CompanyActivity.class));
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Unable to insert item", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}