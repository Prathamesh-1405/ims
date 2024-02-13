package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.view.View;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.Menu;
import android.view.MenuItem;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

public class CatalogActivity extends AppCompatActivity {


    ListView list;
    ArrayAdapter<String> arr;
    OkHttpClient client;
    ArrayList<String> companyArrList = new ArrayList<String>();

    int arrListSize;
    boolean onCreateRunning = false;

    @Override
    protected void onStart() {
        super.onStart();
        if (!onCreateRunning){
            if(arrListSize == 0){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAlert("No companies added ", "Click yes to add compaines");
                    }
                });
            }
            else{
                list.setAdapter(arr);
                updateListView();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        onCreateRunning = true;
        list = findViewById(R.id.list);
        client = new OkHttpClient();
        arr
                = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                companyArrList);
        list.setAdapter(arr);
        updateListView();
        FloatingActionButton fab = findViewById(R.id.AddNewCompanyBtn);


        // Set an OnClickListener for the FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a new activity when the FAB is clicked
                Intent intent = new Intent(CatalogActivity.this, AddNewCompanyActivity.class); // Replace with the name of your new activity
                startActivity(intent);
            }
        });
    }

    private void updateListView(){
        String url = "http://34.201.111.69:5000/company";
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
                try{
                    if(response.isSuccessful()) {
                        String res = response.body().string();
                        JSONObject jsonRes = new JSONObject(res);
                        JSONArray companies = (JSONArray) jsonRes.get("companies");
                        arrListSize = companies.length();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run(){
                                for (int i = 0; i < companies.length(); i++) {
                                    JSONObject tempObj = null;
                                    try {
                                        tempObj = companies.getJSONObject(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        companyArrList.add(tempObj.get("name").toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                list.setAdapter(arr);
                                if(companyArrList.isEmpty()){
                                    showAlert("No companies added ", "Click yes to add compaines");
                                }
                                arr.notifyDataSetChanged();

                            }

                        });
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Get the clicked item
                                String selectedItem = (String) parent.getItemAtPosition(position);
                                // Perform your action here
                                Intent intent = new Intent(CatalogActivity.this, SpecificCompanyActivity.class); // Replace with the name of your new activity
                                intent.putExtra("COMPANY_NAME", selectedItem);
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

    }

    private void showAlert(String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
//        alertDialogBuilder.setIcon(R.drawable.question);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(CatalogActivity.this, AddNewCompanyActivity.class); // Replace with the name of your new activity
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CatalogActivity.this, HomeActivity.class); // Replace with the name of your new activity
                startActivity(intent);
            }
        });
        alertDialogBuilder.show();
    }

}
