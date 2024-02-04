package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class CatalogActivity extends AppCompatActivity {

    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        list = findViewById(R.id.list);
        ArrayList<String> companyArrList = new ArrayList<String>();
        OkHttpClient client = new OkHttpClient();
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                companyArrList);
        list.setAdapter(arr);
        String[] companyList = {};
        String url = "http://54.204.188.232:5000/company";
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)  {
                try{
                    if(response.isSuccessful()) {
                       String res = response.body().string();
                       JSONObject jsonRes = new JSONObject(res);
                       JSONArray companies = (JSONArray) jsonRes.get("companies");

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
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
                               arr.notifyDataSetChanged();

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

        Log.i("CatalogActivity arrlist", companyArrList.toString());
        list.setAdapter(arr);

    }

    private void updateListView(){

    }


}
