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
                startActivity(orderBtnIntent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete company code
                boolean result = deleteCompany(intent.getStringExtra("COMPANY_NAME"));
                startActivity(new Intent(SpecificCompanyActivity.this, CatalogActivity.class));
            }
        });


    }

    private boolean deleteCompany(String companyName) {
        OkHttpClient client = new OkHttpClient();

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("name", companyName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // Define the URL of the resource to be deleted
        String url = "http://54.204.188.232:5000/company/" + companyName;
        String requestObj = paramObject.toString();
        // Create a DELETE request with the specified URL
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestObj);

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody);

        // Execute the request and handle the response
        Request request = requestBuilder.build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                status = false;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    status = true;
                }
            }
        });

    return status;
    }
}