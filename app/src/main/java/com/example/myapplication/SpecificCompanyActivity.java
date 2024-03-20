package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SpecificCompanyActivity extends AppCompatActivity {

    String companyName;
    boolean status = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_company);

        Intent intent = getIntent();
        companyName = intent.getStringExtra("COMPANY_NAME").toString();

        TextView companyTextView = findViewById(R.id.companyNameTextView);

        Button makeOrderBtn = findViewById(R.id.makeOrderBtn);

        Button deleteBtn = findViewById(R.id.deleteBtn);

        companyTextView.setText(intent.getStringExtra("COMPANY_NAME"));
        makeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderBtnIntent = new Intent(SpecificCompanyActivity.this, CompanyActivity.class);
                orderBtnIntent.putExtra("COMPANY_NAME", companyName);
                startActivity(orderBtnIntent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete company code
                deleteCompany(intent.getStringExtra("COMPANY_NAME"));
            }
        });


    }

    private boolean deleteCompany(String companyName) {
        boolean status = false;
        OkHttpClient client = new OkHttpClient();

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("name", companyName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiServiceDetails apiServiceDetails = null;
        String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/remove-company";
        String requestObj = paramObject.toString();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"), // Specify the media type
                paramObject.toString()
        );
        Headers headers = new Headers.Builder()
                .add("source-name", "streamlining-inventory-management")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .headers(headers) // Add your custom headers
                .post(requestBody) // Use POST method with request body
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    if(response.isSuccessful()){
                        startActivity(new Intent(SpecificCompanyActivity.this, CatalogActivity.class));
                        return;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return false;
    }
}