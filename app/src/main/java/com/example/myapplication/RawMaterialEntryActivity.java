package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import retrofit2.Retrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;
public class RawMaterialEntryActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_material_entry);
        Button finishBtn = findViewById(R.id.finishBtn);

//        Spinner spinnertype = findViewById(R.id.typeFieldRm);
//        ArrayAdapter<CharSequence> adaptertype = ArrayAdapter.createFromResource(this, R.array.typeSpinner, android.R.layout.simple_spinner_item);
//        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnertype.setAdapter(adaptertype);
//        spinnertype.setOnItemSelectedListener(this);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner companyNameField = findViewById(R.id.companyNameFieldRm);
                EditText challanNoField = findViewById(R.id.challanNoFieldRm);
                Spinner typeField = findViewById(R.id.typeFieldRm);
                EditText apmChallanNoField = findViewById(R.id.apmChallanNo);
                EditText sizeField = findViewById(R.id.sizeFieldRm);
                EditText quantityField = findViewById(R.id.quantityFieldRm);
                Spinner forField = findViewById(R.id.forFieldRm);
                EditText cuttingSizeField = findViewById(R.id.cuttingSizeFieldRm);
                EditText cuttingWeightField = findViewById(R.id.cuttingWeightFieldRm);
                Spinner orderNoField = findViewById(R.id.orderNoFieldRm);
                EditText orderSizeField = findViewById(R.id.orderSizeFieldRm);

                // getting values through components

                String companyName = companyNameField.getSelectedItem().toString();
                String challanNo = challanNoField.getText().toString();
                String type = typeField.getSelectedItem().toString();
                String apmChallanNo = apmChallanNoField.getText().toString();
                String size = sizeField.getText().toString();
                String quantity = quantityField.getText().toString();
                String forFieldVal = forField.getSelectedItem().toString();
                String cuttingSize = cuttingSizeField.getText().toString();
                String cuttingWeight = cuttingWeightField.getText().toString();
                String orderNo = orderNoField.getSelectedItem().toString();
                String orderSize = orderSizeField.getText().toString();

                try {
                    addRawMaterialEntryFn(companyName, challanNo, type, apmChallanNo, size, quantity, forFieldVal, cuttingSize, cuttingWeight, orderNo, orderSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            private void addRawMaterialEntryFn(String companyName, String challanNo, String type, String apmChallanNo, String size, String quantity, String forFieldVal, String cuttingSize, String cuttingWeight, String orderNo, String orderSize) {
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
                try {
                    paramObj.put("comapny_name", new String(companyName));
                    paramObj.put("challan_no", new String(challanNo));
                    paramObj.put("type_", new String(type));
                    paramObj.put("apm_challan_no", new String(apmChallanNo));
                    paramObj.put("size", new String(size));
                    paramObj.put("quantity", new String(quantity));
                    paramObj.put("purpose_for", new String(forFieldVal));
                    paramObj.put("cutting_size", new String(cuttingSize));
                    paramObj.put("cutting_weight", new String(cuttingWeight));
                    paramObj.put("order_no", new String(orderNo));
                    paramObj.put("order_size", new String(orderSize));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Call<String> apiCall = retrofitAPI.addMaterialEntry(paramObj.toString());
                apiCall.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Toast.makeText(getApplicationContext(), "Data added !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed to add data !", Toast.LENGTH_SHORT).show();

                    }

                });
            }
        });
    }
}

//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String texttype =parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), texttype, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//}