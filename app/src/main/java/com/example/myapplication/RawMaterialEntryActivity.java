package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.BidirectionalStream;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class RawMaterialEntryActivity extends AppCompatActivity  {

    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_material_entry);
        Button finishBtn = findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView companyNameField = findViewById(R.id.companyNameFieldRm);
                EditText challanNoField = findViewById(R.id.challanNoFieldRm);
                AutoCompleteTextView typeField = findViewById(R.id.typeFieldRm);
                EditText apmChallanNoField = findViewById(R.id.apmChallanNo);
                EditText sizeField = findViewById(R.id.sizeFieldRm);
                EditText quantityField = findViewById(R.id.quantityFieldRm);
                AutoCompleteTextView forField = findViewById(R.id.forFieldRm);
                EditText cuttingSizeField = findViewById(R.id.cuttingSizeFieldRm);
                EditText cuttingWeightField = findViewById(R.id.cuttingWeightFieldRm);
                Spinner orderNoField = findViewById(R.id.orderNoFieldRm);
                EditText orderSizeField = findViewById(R.id.orderSizeFieldRm);

                // getting values through components

                String companyName = companyNameField.getText().toString();
                String challanNo = challanNoField.getText().toString();
                String type = typeField.getText().toString();
                String apmChallanNo = apmChallanNoField.getText().toString();
                String size = sizeField.getText().toString();
                String quantity = quantityField.getText().toString();
                String forFieldVal = forField.getText().toString();
                String cuttingSize = cuttingSizeField.getText().toString();
                String cuttingWeight = cuttingWeightField.getText().toString();
                String orderNo = orderNoField.getSelectedItem().toString();
                String orderSize = orderSizeField.getText().toString();

                try {
                    addRawMaterialEntryFn(companyName, challanNo, type, apmChallanNo, size, quantity, forFieldVal, cuttingSize, cuttingWeight, orderNo, orderSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                // Passing the inputs to the QRCodeActivity
                Intent intent = new Intent(RawMaterialEntryActivity.this, GenerateQrActivity.class);
                intent.putExtra("orderNo", orderNo);
                intent.putExtra("cuttingSize", cuttingSize);
                intent.putExtra("cuttingWeight", cuttingWeight);
                startActivity(intent);

            }

            private void addRawMaterialEntryFn(String companyName, String challanNo, String type, String apmChallanNo, String size, String quantity, String forFieldVal, String cuttingSize, String cuttingWeight, String orderNo, String orderSize) {
                OkHttpClient client = new OkHttpClient();

                JSONObject paramObj = new JSONObject();
                try {
                    paramObj.put("company_name", new String(companyName));
                    paramObj.put("challan_no", new String(challanNo));
                    paramObj.put("type", new String(type));
                    paramObj.put("apm_challan_no", new String(apmChallanNo));
                    paramObj.put("size", new String(size));
                    paramObj.put("quantity", new String(quantity));
                    paramObj.put("purpose_for", new String(forFieldVal));
                    paramObj.put("cutting_size", new String(cuttingSize));
                    paramObj.put("cutting_weight", new String(cuttingWeight));
                    paramObj.put("order_no", new String(orderNo));
                    paramObj.put("order_size", new String(orderSize));
                    paramObj.put("stage", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiServiceDetails apiServiceDetails = null;
//                String url = "http://172.20.10.3:5000/add-material";
                String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/add-material";
                String requestObj = paramObj.toString();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestObj);

                Request.Builder requestBuilder = new Request.Builder()
                        .url(url).post(requestBody);

                String SOURCE_NAME = "streamlining-inventory-management";
                requestBuilder.addHeader("Content-Type", "application/json");
                requestBuilder.addHeader("source-name", SOURCE_NAME);
                Request request = requestBuilder.build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response){
                        if (response.isSuccessful()) {
                            String responseBody = response.body().toString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Raw material added !", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Unable to add data !", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(getApplicationContext(), "Unable to add raw material !", Toast.LENGTH_SHORT).show();

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
//}package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.net.http.BidirectionalStream;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import android.widget.Spinner;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//public class RawMaterialEntryActivity extends AppCompatActivity  {
//
//    OkHttpClient client;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_raw_material_entry);
//        Button finishBtn = findViewById(R.id.finishBtn);
//        finishBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AutoCompleteTextView companyNameField = findViewById(R.id.companyNameFieldRm);
//                EditText challanNoField = findViewById(R.id.challanNoFieldRm);
//                AutoCompleteTextView typeField = findViewById(R.id.typeFieldRm);
//                EditText apmChallanNoField = findViewById(R.id.apmChallanNo);
//                EditText sizeField = findViewById(R.id.sizeFieldRm);
//                EditText quantityField = findViewById(R.id.quantityFieldRm);
//                AutoCompleteTextView forField = findViewById(R.id.forFieldRm);
//                EditText cuttingSizeField = findViewById(R.id.cuttingSizeFieldRm);
//                EditText cuttingWeightField = findViewById(R.id.cuttingWeightFieldRm);
//                AutoCompleteTextView orderNoField = findViewById(R.id.orderNoFieldRm);
//                EditText orderSizeField = findViewById(R.id.orderSizeFieldRm);
//
//                // getting values through components
//
//                String companyName = companyNameField.getText().toString();
//                String challanNo = challanNoField.getText().toString();
//                String type = typeField.getText().toString();
//                String apmChallanNo = apmChallanNoField.getText().toString();
//                String size = sizeField.getText().toString();
//                String quantity = quantityField.getText().toString();
//                String forFieldVal = forField.getText().toString();
//                String cuttingSize = cuttingSizeField.getText().toString();
//                String cuttingWeight = cuttingWeightField.getText().toString();
//                String orderNo = orderNoField.getText().toString();
//                String orderSize = orderSizeField.getText().toString();
//
//                try {
//                    addRawMaterialEntryFn(companyName, challanNo, type, apmChallanNo, size, quantity, forFieldVal, cuttingSize, cuttingWeight, orderNo, orderSize);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                // Passing the inputs to the QRCodeActivity
//                Intent intent = new Intent(RawMaterialEntryActivity.this, GenerateQrActivity.class);
//                intent.putExtra("orderNo", orderNo);
//                intent.putExtra("cuttingSize", cuttingSize);
//                intent.putExtra("cuttingWeight", cuttingWeight);
//                startActivity(intent);
//
//            }
//
//            private void addRawMaterialEntryFn(String companyName, String challanNo, String type, String apmChallanNo, String size, String quantity, String forFieldVal, String cuttingSize, String cuttingWeight, String orderNo, String orderSize) {
//                OkHttpClient client = new OkHttpClient();
//
//                JSONObject paramObj = new JSONObject();
//                try {
//                    paramObj.put("company_name", new String(companyName));
//                    paramObj.put("challan_no", new String(challanNo));
//                    paramObj.put("type", new String(type));
//                    paramObj.put("apm_challan_no", new String(apmChallanNo));
//                    paramObj.put("size", new String(size));
//                    paramObj.put("quantity", new String(quantity));
//                    paramObj.put("purpose_for", new String(forFieldVal));
//                    paramObj.put("cutting_size", new String(cuttingSize));
//                    paramObj.put("cutting_weight", new String(cuttingWeight));
//                    paramObj.put("order_no", new String(orderNo));
//                    paramObj.put("order_size", new String(orderSize));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                String url = "http://192.168.1.122:5000/add-material";
//
//                String requestObj = paramObj.toString();
//                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestObj);
//
//                Request.Builder requestBuilder = new Request.Builder()
//                        .url(url).post(requestBody);
//
//                String SOURCE_NAME = "streamlining-inventory-management";
//                requestBuilder.addHeader("Content-Type", "application/json");
//                requestBuilder.addHeader("source-name", SOURCE_NAME);
//                Request request = requestBuilder.build();
//
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onResponse(Call call, Response response){
//                        if (response.isSuccessful()) {
//                            String responseBody = response.body().toString();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(getApplicationContext(), "Raw material added !", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                            startActivity(new Intent(RawMaterialEntryActivity.this, HomeActivity.class));
//
//                        } else {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(getApplicationContext(), "Unable to add data !", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Toast.makeText(getApplicationContext(), "Unable to add raw material !", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//
//            });
//
//    }
//
//
//}
//
////
////    @Override
////    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////        String texttype =parent.getItemAtPosition(position).toString();
////        Toast.makeText(parent.getContext(), texttype, Toast.LENGTH_SHORT).show();
////    }
////
////    @Override
////    public void onNothingSelected(AdapterView<?> parent) {
////
////    }
////}package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.net.http.BidirectionalStream;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import android.widget.Spinner;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//public class RawMaterialEntryActivity extends AppCompatActivity  {
//
//    OkHttpClient client;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_raw_material_entry);
//        Button finishBtn = findViewById(R.id.finishBtn);
//        finishBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AutoCompleteTextView companyNameField = findViewById(R.id.companyNameFieldRm);
//                EditText challanNoField = findViewById(R.id.challanNoFieldRm);
//                AutoCompleteTextView typeField = findViewById(R.id.typeFieldRm);
//                EditText apmChallanNoField = findViewById(R.id.apmChallanNo);
//                EditText sizeField = findViewById(R.id.sizeFieldRm);
//                EditText quantityField = findViewById(R.id.quantityFieldRm);
//                AutoCompleteTextView forField = findViewById(R.id.forFieldRm);
//                EditText cuttingSizeField = findViewById(R.id.cuttingSizeFieldRm);
//                EditText cuttingWeightField = findViewById(R.id.cuttingWeightFieldRm);
//                AutoCompleteTextView orderNoField = findViewById(R.id.orderNoFieldRm);
//                EditText orderSizeField = findViewById(R.id.orderSizeFieldRm);
//
//                // getting values through components
//
//                String companyName = companyNameField.getText().toString();
//                String challanNo = challanNoField.getText().toString();
//                String type = typeField.getText().toString();
//                String apmChallanNo = apmChallanNoField.getText().toString();
//                String size = sizeField.getText().toString();
//                String quantity = quantityField.getText().toString();
//                String forFieldVal = forField.getText().toString();
//                String cuttingSize = cuttingSizeField.getText().toString();
//                String cuttingWeight = cuttingWeightField.getText().toString();
//                String orderNo = orderNoField.getText().toString();
//                String orderSize = orderSizeField.getText().toString();
//
//                try {
//                    addRawMaterialEntryFn(companyName, challanNo, type, apmChallanNo, size, quantity, forFieldVal, cuttingSize, cuttingWeight, orderNo, orderSize);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                // Passing the inputs to the QRCodeActivity
//                Intent intent = new Intent(RawMaterialEntryActivity.this, GenerateQrActivity.class);
//                intent.putExtra("orderNo", orderNo);
//                intent.putExtra("cuttingSize", cuttingSize);
//                intent.putExtra("cuttingWeight", cuttingWeight);
//                startActivity(intent);
//
//            }
//
//            private void addRawMaterialEntryFn(String companyName, String challanNo, String type, String apmChallanNo, String size, String quantity, String forFieldVal, String cuttingSize, String cuttingWeight, String orderNo, String orderSize) {
//                OkHttpClient client = new OkHttpClient();
//
//                JSONObject paramObj = new JSONObject();
//                try {
//                    paramObj.put("company_name", new String(companyName));
//                    paramObj.put("challan_no", new String(challanNo));
//                    paramObj.put("type", new String(type));
//                    paramObj.put("apm_challan_no", new String(apmChallanNo));
//                    paramObj.put("size", new String(size));
//                    paramObj.put("quantity", new String(quantity));
//                    paramObj.put("purpose_for", new String(forFieldVal));
//                    paramObj.put("cutting_size", new String(cuttingSize));
//                    paramObj.put("cutting_weight", new String(cuttingWeight));
//                    paramObj.put("order_no", new String(orderNo));
//                    paramObj.put("order_size", new String(orderSize));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                String url = "http://192.168.1.122:5000/add-material";
//
//                String requestObj = paramObj.toString();
//                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestObj);
//
//                Request.Builder requestBuilder = new Request.Builder()
//                        .url(url).post(requestBody);
//
//                String SOURCE_NAME = "streamlining-inventory-management";
//                requestBuilder.addHeader("Content-Type", "application/json");
//                requestBuilder.addHeader("source-name", SOURCE_NAME);
//                Request request = requestBuilder.build();
//
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onResponse(Call call, Response response){
//                        if (response.isSuccessful()) {
//                            String responseBody = response.body().toString();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(getApplicationContext(), "Raw material added !", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                            startActivity(new Intent(RawMaterialEntryActivity.this, HomeActivity.class));
//
//                        } else {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(getApplicationContext(), "Unable to add data !", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Toast.makeText(getApplicationContext(), "Unable to add raw material !", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//
//            });
//
//    }
//
//
//}
//
////
////    @Override
////    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////        String texttype =parent.getItemAtPosition(position).toString();
////        Toast.makeText(parent.getContext(), texttype, Toast.LENGTH_SHORT).show();
////    }
////
////    @Override
////    public void onNothingSelected(AdapterView<?> parent) {
////
////    }
////}