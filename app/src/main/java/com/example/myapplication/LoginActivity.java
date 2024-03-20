package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = findViewById(R.id.buttonLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                OkHttpClient okHttpClient = new OkHttpClient();
                ApiServiceDetails apiServiceDetails = null;
                String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/login";


                EditText username = findViewById(R.id.editTextLoginUsername);
                EditText password = findViewById(R.id.editTextLoginPassword);
                JSONObject jsonObject  = new JSONObject();
                try {
                    jsonObject.put("username", username.getText().toString());
                    jsonObject.put("password", password.getText().toString());

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
                Request.Builder requestBuilder = new Request.Builder()
                        .url(url).post(requestBody);
                String SOURCE_NAME = "streamlining-inventory-management";
                requestBuilder.addHeader("Content-Type", "application/json");
                requestBuilder.addHeader("source-name", SOURCE_NAME);
                Request request = requestBuilder.build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {
                        try{
                            if(response.isSuccessful()){
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }
                            else if(response.code() == 401){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid username or password !", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                            else if(response.code() == 406){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Request cannot be processed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else if(response.code() == 400){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Username and password are required !", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}