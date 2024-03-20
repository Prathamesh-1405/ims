package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        findViewById(R.id.stage1TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage1();
//                showPopup(R.layout.popup_stage1, R.id.finish_button1);
            }
        });

        findViewById(R.id.stage2TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage2();
            }
        });

        findViewById(R.id.stage3TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage3();
            }
        });

        findViewById(R.id.stage4TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage4();
            }
        });

        findViewById(R.id.stage5TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage5();
            }
        });
    }



    private void setupStage1() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.popup_layout, null);
        LinearLayout container = dialogView.findViewById(R.id.container_popup);
        OkHttpClient okHttpClient = new OkHttpClient();
        ApiServiceDetails apiServiceDetails = null;
        String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/item";
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try{
                    if(response.isSuccessful()){
                        String res = response.body().string();
                        JSONObject jsonRes = new JSONObject(res);
                        JSONArray items = (JSONArray) jsonRes.get("Items");
                        int resArrLen = 0;
                        Log.i("ProcessActivity", items.toString());
                        for (int i = 0; i < items.length(); i++) {
                            Log.i("ProcessActivity", items.getJSONObject(i).get("stage").toString());
                            View rowView = inflater.inflate(R.layout.update_order_item_layout, null);
                            TextView itemNameTextView = rowView.findViewById(R.id.itemNameTextView);
                            TextView totalQtyTextView = rowView.findViewById(R.id.totalQtyTextView);
                            EditText completedQtyEditText = rowView.findViewById(R.id.completedQtyEditText);
                            if(items.getJSONObject(i).get("stage").toString().equals("1")){
                                itemNameTextView.setText(items.getJSONObject(i).get("item_name").toString());
                                totalQtyTextView.setText(items.getJSONObject(i).get("total_quantity").toString());
                                container.addView(rowView);
                                resArrLen++;
                            }
                        }
                        if(resArrLen != 0){
                            builder.setView(dialogView)
                                    .setTitle("Stage 1")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // action to be performed
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No items present in stage 1", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });



    }


    //for stage 2
    private void setupStage2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.popup_layout, null);
        LinearLayout container = dialogView.findViewById(R.id.container_popup);

        OkHttpClient okHttpClient = new OkHttpClient();
        ApiServiceDetails apiServiceDetails = null;
        String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/item";
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try{
                    if(response.isSuccessful()){
                        String res = response.body().string();
                        JSONObject jsonRes = new JSONObject(res);
                        JSONArray items = (JSONArray) jsonRes.get("Items");
                        int resArrLen = 0;
                        Log.i("ProcessActivity", items.toString());
                        for (int i = 0; i < items.length(); i++) {
                            Log.i("ProcessActivity", items.getJSONObject(i).get("stage").toString());
                            View rowView = inflater.inflate(R.layout.update_order_item_layout, null);
                            TextView itemNameTextView = rowView.findViewById(R.id.itemNameTextView);
                            TextView totalQtyTextView = rowView.findViewById(R.id.totalQtyTextView);
                            EditText completedQtyEditText = rowView.findViewById(R.id.completedQtyEditText);
                            if(items.getJSONObject(i).get("stage").toString().equals("2")){
                                itemNameTextView.setText(items.getJSONObject(i).get("item_name").toString());
                                totalQtyTextView.setText(items.getJSONObject(i).get("total_quantity").toString());
                                container.addView(rowView);
                                resArrLen++;
                            }

                        }
                        if(resArrLen != 0){
                            builder.setView(dialogView)
                                    .setTitle("Stage 2")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // action to be performed
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No items present in stage 2", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    //for stage 3

    private void setupStage3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.popup_layout, null);
        LinearLayout container = dialogView.findViewById(R.id.container_popup);

        OkHttpClient okHttpClient = new OkHttpClient();
        ApiServiceDetails apiServiceDetails = null;
        String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/item";
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try{
                    if(response.isSuccessful()){
                        String res = response.body().string();
                        JSONObject jsonRes = new JSONObject(res);
                        JSONArray items = (JSONArray) jsonRes.get("Items");
                        int resArrLen = 0;
                        Log.i("ProcessActivity", items.toString());
                        for (int i = 0; i < items.length(); i++) {
                            Log.i("ProcessActivity", items.getJSONObject(i).get("stage").toString());
                            View rowView = inflater.inflate(R.layout.update_order_item_layout, null);
                            TextView itemNameTextView = rowView.findViewById(R.id.itemNameTextView);
                            TextView totalQtyTextView = rowView.findViewById(R.id.totalQtyTextView);
                            EditText completedQtyEditText = rowView.findViewById(R.id.completedQtyEditText);
                            if(items.getJSONObject(i).get("stage").toString().equals("3")){
                                itemNameTextView.setText(items.getJSONObject(i).get("item_name").toString());
                                totalQtyTextView.setText(items.getJSONObject(i).get("total_quantity").toString());
                                container.addView(rowView);
                                resArrLen++;
                            }


                        }
                        if (resArrLen != 0){
                            builder.setView(dialogView)
                                    .setTitle("Stage 3")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // action to be performed
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No items present in stage 3", Toast.LENGTH_LONG).show();

                        }
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });



    }

    //for stage 4

    private void setupStage4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.popup_layout, null);
        LinearLayout container = dialogView.findViewById(R.id.container_popup);

        OkHttpClient okHttpClient = new OkHttpClient();
        ApiServiceDetails apiServiceDetails = null;
        String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/item";
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try{
                    if(response.isSuccessful()){
                        String res = response.body().string();
                        JSONObject jsonRes = new JSONObject(res);
                        JSONArray items = (JSONArray) jsonRes.get("Items");
                        int resArrLen = 0;
                        Log.i("ProcessActivity", items.toString());
                        for (int i = 0; i < items.length(); i++) {
                            Log.i("ProcessActivity", items.getJSONObject(i).get("stage").toString());
                            View rowView = inflater.inflate(R.layout.update_order_item_layout, null);
                            TextView itemNameTextView = rowView.findViewById(R.id.itemNameTextView);
                            TextView totalQtyTextView = rowView.findViewById(R.id.totalQtyTextView);
                            EditText completedQtyEditText = rowView.findViewById(R.id.completedQtyEditText);
                            if(items.getJSONObject(i).get("stage").toString().equals("4")){
                                itemNameTextView.setText(items.getJSONObject(i).get("item_name").toString());
                                totalQtyTextView.setText(items.getJSONObject(i).get("total_quantity").toString());
                                container.addView(rowView);
                                resArrLen++;
                            }

                        }
                        if(resArrLen != 0){
                            builder.setView(dialogView)
                                    .setTitle("Stage 4")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // action to be performed
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No items present in stage 4", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });



    }

    //for stage 5

    private void setupStage5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.popup_layout, null);
        LinearLayout container = dialogView.findViewById(R.id.container_popup);

        OkHttpClient okHttpClient = new OkHttpClient();
        ApiServiceDetails apiServiceDetails = null;
        String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/item";
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try{
                    if(response.isSuccessful()){
                        String res = response.body().string();
                        JSONObject jsonRes = new JSONObject(res);
                        JSONArray items = (JSONArray) jsonRes.get("Items");
                        int resArrLen = 0;
                        Log.i("ProcessActivity", items.toString());
                        for (int i = 0; i < items.length(); i++) {
                            Log.i("ProcessActivity", items.getJSONObject(i).get("stage").toString());
                            View rowView = inflater.inflate(R.layout.update_order_item_layout, null);
                            TextView itemNameTextView = rowView.findViewById(R.id.itemNameTextView);
                            TextView totalQtyTextView = rowView.findViewById(R.id.totalQtyTextView);
                            EditText completedQtyEditText = rowView.findViewById(R.id.completedQtyEditText);
                            if(items.getJSONObject(i).get("stage").toString().equals("1")){
                                itemNameTextView.setText(items.getJSONObject(i).get("item_name").toString());
                                totalQtyTextView.setText(items.getJSONObject(i).get("total_quantity").toString());
                                container.addView(rowView);
                                resArrLen++;
                            }

                        }
                        if(resArrLen != 0){
                            builder.setView(dialogView)
                                    .setTitle("Stage 5")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // action to be performed
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No items present in stage 5", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });



    }

}