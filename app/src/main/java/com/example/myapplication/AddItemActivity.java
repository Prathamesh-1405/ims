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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

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

                try {
                    addItem(itemVal, rodDiameterVal, unitWeightVal, unitPriceVal, quantityVal, totalVal);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }


        });
    }

    private void addItem(String itemVal, String rodDiameterVal, String unitWeightVal, String unitPriceVal, String quantiyVal, String totalVal) throws Exception {
        String url = "http://34.201.111.69:5000/add-item";


        JSONObject paramObj = new JSONObject();
        paramObj.put("item", new String(itemVal));
        paramObj.put("rod_diameter", new String(rodDiameterVal));
        paramObj.put("unit_weight", new String(unitWeightVal));
        paramObj.put("unit_price", new String(unitPriceVal));
        paramObj.put("quantity", new String(quantiyVal));
        paramObj.put("total", new String(totalVal));

        String requestObj = paramObj.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestObj);

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody);

        String SOURCE_NAME = "streamlining-inventory-management";
        requestBuilder.addHeader("Content-Type", "application/json");
        requestBuilder.addHeader("source-name", SOURCE_NAME);
        Request request = requestBuilder.build();
        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                try{
                    String res = response.body().toString();
                    if(response.isSuccessful()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Item added !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddItemActivity.this, CompanyActivity.class));
                            }
                        });
                    }
                    else if(response.code() != 201){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Unable to add item !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
    }

}