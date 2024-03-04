package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PurchaseActivity extends AppCompatActivity {

    public String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayList items = new ArrayList();
        // Example data for AutoCompleteTextView
//        String companyJsonString = fetchCompanyNames();
//        List<String> list = null;
//        try {
//            JSONObject jsonObject = new JSONObject(companyJsonString);
//            String companies  = (String)jsonObject.get("companies");
//            list = new ArrayList<String>(Collections.singleton(companies));
//            for (int i = 0; i < list.size(); i++) {
//                items.add(list.get(i));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        // Create an ArrayAdapter with the data
//        String companyArr[] = (String[]) items.toArray();
//        String companyArr[] = new String[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            companyArr[i] = list.get(i);
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, companyArr);

        // Set the adapter to AutoCompleteTextView
//        autoCompleteTextView.setAdapter(adapter);

    }

    private String fetchCompanyNames() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(RetrofitAPICall.URL_BASE)
//                // as we are sending data in json format so
//                // we have to add Gson converter factory
//                .addConverterFactory(GsonConverterFactory.create())
//                // at last we are building our retrofit builder.
//                .build();
//        RetrofitAPICall retrofitAPI = retrofit.create(RetrofitAPICall.class);
//
//        Call<String> apiCall = retrofitAPI.getCompanyNames();
//        apiCall.enqueue(new Callback<String>() {
//
//            @Override
//            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                if (response.isSuccessful()) {
//                    data = response.body();
//                    // Process the data here
//                } else {
//                    // Handle error
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                // Handle failure
//            }
//
//
//        });
//
//        if (data == null){
//            return "";
//
//        }
//        else {
//            return data;
//        }
        return "Empty_String";
        }

    public void onClickCompanyName(View view) {
        startActivity(new Intent(PurchaseActivity.this, CompanyActivity.class));
    }

    public void onClickAdd(View view) {
        startActivity(new Intent(PurchaseActivity.this, AddNewCompanyActivity.class));
    }
}



//package com.example.viruspinner;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.ArrayList;
//import java.util.List;

//public class MainActivity extends AppCompatActivity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        EditText customInput = findViewById(R.id.customInput);
        Button addButton = findViewById(R.id.addButton); // Add this line

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Item 1");
        spinnerItems.add("Item 2");
        spinnerItems.add("Item 3");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle item selection in the Spinner
                String selectedValue = spinner.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, "Selected: " + selectedValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle adding custom value from EditText to the Spinner
                String customValue = customInput.getText().toString();
                if (!customValue.isEmpty()) {
                    spinnerItems.add(customValue);
                    spinnerAdapter.notifyDataSetChanged();
                    customInput.setText("");
                }
            }
        });
    }*/
