package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.UFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CompanyActivity extends AppCompatActivity {
    ListView itemListView;
    ArrayAdapter<String> arr;

    List<Item> itemList;
    OkHttpClient client;
    ItemDAO itemDAO;

    ArrayList<String> arrItemList = new ArrayList<String>();

    boolean onCreatRunning = false;
    String companyName;
    int lenOfResonseList;
    int arrListSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        onCreatRunning = true;
        this.itemListView = findViewById(R.id.itemList);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button finishBtn = findViewById(R.id.orderFinishBtn);
        arr
                = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrItemList
        );

        itemListView.setAdapter(arr);
        updateListView();
        FloatingActionButton fabAddBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        fabAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyActivity.this, AddItemActivity.class));
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText orderNoField = findViewById(R.id.orderNoField);
        EditText rateField = findViewById(R.id.rateField);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderNo = orderNoField.getText().toString();
                String rate = rateField.getText().toString();
                if (orderNo.isEmpty() || rate.isEmpty()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please fill out all text fields !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                itemList = itemDAO.getAllItems();
                ArrayList<JSONObject> items = new ArrayList<>();
                Intent intent = getIntent();
                String companyName = intent.getStringExtra("COMPANY_NAME");
                for (int i = 0; i < itemList.size(); i++) {

                    JSONObject temp = new JSONObject();
                    try {
                        temp.put("item_name", itemList.get(i).getItemName());
                        temp.put("rod_diameter", itemList.get(i).getRodDiameter());
                        temp.put("unit_weight", itemList.get(i).getUnitWeight());
                        temp.put("unit_price", itemList.get(i).getUnitPrice());
                        temp.put("quantity", itemList.get(i).getQuantity());
                        temp.put("total", itemList.get(i).getTotal());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    items.add(temp);


                }

                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("company_name", companyName);
                    jsonData.put("order_no", orderNo);
                    jsonData.put("rate", rate);
                    jsonData.put("items", items.toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                OkHttpClient client  = new OkHttpClient();

                String url = "http://34.201.111.69:5000/add-order";

                String requestObj = jsonData.toString();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestObj);

                Request.Builder requestBuilder = new Request.Builder()
                        .url(url).post(requestBody);

                String SOURCE_NAME = "streamlining-inventory-management";
                requestBuilder.addHeader("Content-Type", "application/json");
                requestBuilder.addHeader("source-name", SOURCE_NAME);
                Request request = requestBuilder.build();

                if (orderNo.isEmpty() != true && rate.isEmpty() != true){
                    DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                    databaseHelper.truncateTable();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Order added successfully  !", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                startActivity(new Intent(CompanyActivity.this, HomeActivity.class));
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Unable to add order !", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }


                    });

                }
            }
        });




    }

    private void updateListView(){
        itemListView = findViewById(R.id.itemList);
        itemDAO = new ItemDAO(this);
        itemList = itemDAO.getAllItems();
//        Log.i("CompanyActivity", itemList.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < itemList.size(); i++) {
                    arrItemList.add(itemList.get(i).getItemName());
                }
            }
        });
    }

    public void onClickAddItemBtn(View view) {
        startActivity(new Intent(CompanyActivity.this, AddItemActivity.class));
    }
}