package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddItemActivity extends AppCompatActivity {
    Button addItemBtn = findViewById(R.id.addItemBtn);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
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

            private void addItem(String itemVal,String rodDiameterVal, String unitWeightVal, String unitPriceVal, String quantiyVal, String totalVal) throws Exception
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RetrofitAPICall.URL_BASE)
                        // as we are sending data in json format so
                        // we have to add Gson converter factory
                        .addConverterFactory(GsonConverterFactory.create())
                        // at last we are building our retrofit builder.
                        .build();
                // below line is to create an instance for our retrofit api class.
                RetrofitAPICall retrofitAPI = retrofit.create(RetrofitAPICall.class);
                JSONObject paramObj = new JSONObject();
                paramObj.put("item", new String(itemVal));
                paramObj.put("rod_diameter", new String(rodDiameterVal));
                paramObj.put("unit_weight", new String(unitWeightVal));
                paramObj.put("unit_price", new String(unitPriceVal));
                paramObj.put("quantity", new String(quantiyVal));
                paramObj.put("total", new String(totalVal));

                Call<String> apiCall = retrofitAPI.addMaterialEntry(paramObj.toString());
                apiCall.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Toast.makeText(getApplicationContext(),"Data added !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Failed to add data !", Toast.LENGTH_SHORT).show();

                    }

                });

            }
        });
    }

}