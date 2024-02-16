package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CompanyActivity extends AppCompatActivity {
    ListView itemListView;
    ArrayAdapter<String> arr;

    List<Item> itemList;
    OkHttpClient client;
    ItemDAO itemDAO;

    ArrayList<String> arrItemList = new ArrayList<String>();

    boolean onCreatRunning = false;

    int lenOfResonseList;
    int arrListSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        onCreatRunning = true;
        this.itemListView = findViewById(R.id.itemList);

        arr
                = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrItemList
        );

        itemListView.setAdapter(arr);
//        updateListView();
        FloatingActionButton fabAddBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        fabAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyActivity.this, AddItemActivity.class));
            }
        });


    }

    private void updateListView(){
        String url = "http://34.201.111.69:5000/item";
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Unable to fetch data !", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)  {
                try {
                    Log.i("CompanyActivity", response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try{
                    if(response.isSuccessful()) {
                        String res = response.body().string();
                        JSONObject jsonRes = new JSONObject(res);
                        JSONArray items = (JSONArray) jsonRes.get("Items");
                        arrListSize = items.length();
                        Log.i("CompanyActivity", res);
                        for (int i = 0; i < arrListSize; i++) {
                            JSONObject tempObj = null;
                            try {
                                tempObj = items.getJSONObject(i);
                                Log.i("CompanyActivity", tempObj.toString());
                                arrItemList.add(tempObj.get("item_name").toString());
                                Log.i("CompanyActivity", tempObj.get("item_name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run(){

                                itemListView.setAdapter(arr);
                                arr.notifyDataSetChanged();

                            }

                        });
                        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Get the clicked item
                                String selectedItem = (String) parent.getItemAtPosition(position);
                                // Perform your action here
                                Intent intent = new Intent(CompanyActivity.this, SpecificCompanyActivity.class); // Replace with the name of your new activity
                                intent.putExtra("ITEM_NAME", selectedItem);
                                startActivity(intent);
                            }
                        });

                    }
                    else if(response.code() == 422){
                        Toast.makeText(getApplicationContext(), "Invalid GST No", Toast.LENGTH_SHORT);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Unable to fetch Data !", Toast.LENGTH_SHORT);
                    }

                }
                catch(Exception e){
                    Log.i("CatalogActivity", e.getMessage());
                }

            }


        });

        itemList = itemDAO.getAllItems();
        lenOfResonseList  = itemList.size();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < lenOfResonseList; i++) {
                    arrItemList.add(itemList.get(i).getItemName());
                }
                itemListView.setAdapter(arr);
                arr.notifyDataSetChanged();
            }
        });

    }

    public void onClickAddItemBtn(View view) {
        startActivity(new Intent(CompanyActivity.this, AddItemActivity.class));
    }
}