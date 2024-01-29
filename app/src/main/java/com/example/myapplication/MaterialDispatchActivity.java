package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MaterialDispatchActivity extends AppCompatActivity {
    Button finishBtn = findViewById(R.id.finishBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_dispatch);


        finishBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                EditText orderNumber = findViewById(R.id.orderNoFieldmd);
                EditText date = findViewById(R.id.dateFieldmd);
                EditText time = findViewById(R.id.timeFieldmd);
                EditText netWeight = findViewById(R.id.netWeightFieldmd);
                EditText apmBill = findViewById(R.id.apmBillNoFieldmd);
                EditText vehicleNumber = findViewById(R.id.vehicleNumberFieldmd);

                String orderNumberVal = orderNumber.getText().toString();
                String dateVal = date.getText().toString();
                String timeVal = time.getText().toString();
                String netWeightVal = netWeight.getText().toString();
                String apmBillVal = apmBill.getText().toString();
                String vehicleNumberVal = vehicleNumber.getText().toString();

                try {
                    finishBtn(orderNumberVal, dateVal, timeVal, netWeightVal, apmBillVal, vehicleNumberVal);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            private void finishBtn(String orderNumberVal,String dateVal, String timeVal, String netWeightVal, String apmBillVal, String vehicleNumberVal) throws Exception
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RetrofitAPICall.URL_BASE)
                        // as we are sending data in json format so
                        // we have to add Gson converter factory
                        .addConverterFactory(GsonConverterFactory.create())
                        // at last we are building our retrofit builder.
                        .build();
                // below line is to create an instance for our retrofit api class.
                RetrofitAPICall retrofitAPI = retrofit.create(RetrofitAPICall.class);
                JSONObject paramObj = new JSONObject();
                paramObj.put("order_number", new String(orderNumberVal));
                paramObj.put("date", new String(dateVal));
                paramObj.put("time", new String(timeVal));
                paramObj.put("net_weight", new String(netWeightVal));
                paramObj.put("apm_bill", new String(apmBillVal));
                paramObj.put("vehicle_number", new String(vehicleNumberVal));

                Call<String> apiCall = retrofitAPI.materialDispatch(paramObj.toString());

                apiCall.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Toast.makeText(getApplicationContext(),"Data added !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Failed to add data !", Toast.LENGTH_SHORT).show();

                    }

                });
            }
        });
    }
}