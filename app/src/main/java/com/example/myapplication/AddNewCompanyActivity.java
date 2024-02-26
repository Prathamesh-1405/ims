package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import okhttp3.*;
import retrofit2.Retrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewCompanyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button addCompanyBtn;
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_company);
        addCompanyBtn = findViewById(R.id.addCompanyBtn);

        AutoCompleteTextView spinner1 = findViewById(R.id.stateField);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.stateSpinner, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.companyInSezField);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.companyInSezSpinner, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        Spinner spinner3 = findViewById(R.id.companyTypeField);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.companyTypeSpinner, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);

        Spinner spinner4 = findViewById(R.id.supplierTypeField);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.supplierTypeSpinner, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setOnItemSelectedListener(this);

        addCompanyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText companyNameField = findViewById(R.id.companyNameField);
                EditText addressField = findViewById(R.id.addressField);
                EditText cityField = findViewById(R.id.cityField);
                EditText pincodeField = findViewById(R.id.pinCodeField);
                AutoCompleteTextView stateSelectMenu = findViewById(R.id.stateField);
                EditText gstNoField = findViewById(R.id.gstNoField);
                Spinner companyInSezField = findViewById(R.id.companyInSezField);
                Spinner companyTypeField = findViewById(R.id.companyTypeField);
                Spinner supplierTypeField = findViewById(R.id.supplierTypeField);
                EditText distanceFromAndheriField = findViewById(R.id.distanceFromAndheriField);
                EditText distanceFromVasaiField = findViewById(R.id.distanceFromVasaiField);

//                getting values
                String companyName = companyNameField.getText().toString();
                String address = addressField.getText().toString();
                String city = cityField.getText().toString();
                String pinCode = pincodeField.getText().toString();
                String state = stateSelectMenu.getText().toString();
                String gstNo = gstNoField.getText().toString();
                String companyInSez = companyInSezField.getSelectedItem().toString();
                String companyType = companyTypeField.getSelectedItem().toString();
                String supplierType = supplierTypeField.getSelectedItem().toString();
                Float distanceFromAndheri = Float.parseFloat(distanceFromAndheriField.getText().toString());
                Float distanceFromVasai = Float.parseFloat(distanceFromVasaiField.getText().toString());
                Log.d("AddNewCompanyActivity","Data collected");
                postData(companyName, address, city, pinCode, state, gstNo, companyInSez, companyType, supplierType, distanceFromAndheri, distanceFromVasai);

                Log.d("AddNewCompanyActivity", "After network request");

            }
        });
    }

    private void postData(String name, String address, String city, String pincode, String state, String gstNo, String companyInSez, String companyType, String supplierType, Float distanceFromAndheri, Float distanceFromVasai) {


        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("name", new String(name));
            paramObject.put("address", new String(address));
            paramObject.put("city", new String(city));
            paramObject.put("pincode", new String(pincode));
            paramObject.put("state", new String(state));
            paramObject.put("gst_no", new String(gstNo));
            paramObject.put("company_in_sez", new String(companyInSez));
            paramObject.put("company_type", new String(companyType));
            paramObject.put("supplier_type", new String(supplierType));
            paramObject.put("distance_from_andheri", new Float(distanceFromAndheri).toString());
            paramObject.put("distance_from_vasai", new Float(distanceFromVasai).toString());

//            Call<CompanyObject> apiCall = retrofitAPI.addCompany(new CompanyObject(name,address,city,pincode, state, gstNo, companyInSez, companyType, supplierType, distanceFromAndheri, distanceFromVasai));

            String url = "http://34.201.111.69:5000/add-company";

            String requestObj = paramObject.toString();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestObj);

            Request.Builder requestBuilder = new Request.Builder()
                    .url(url).post(requestBody);

            String SOURCE_NAME = "streamlining-inventory-management";
            requestBuilder.addHeader("Content-Type", "application/json");
            requestBuilder.addHeader("source-name", SOURCE_NAME);
            Request request = requestBuilder.build();
//            async function call .addHeader('Source-Name', 'streamlining-inventory-management');
            Log.d("AddNewCompanyActivity", "Before api call");

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            Toast.makeText(getApplicationContext(), "Unable to add data !", Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response){
                    Log.i("NewCompanyActivity","Onresponse" );
                    try {
                        String res= response.body().toString();
                        Log.i("NewCompanyActivity","Response body : " + res);
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(), "Data Added !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddNewCompanyActivity.this, CatalogActivity.class);
                                    startActivity(intent);
                                }


                            });


                        } else if (response.code() == 422) {
                            runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(), "Invalid GST no", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddNewCompanyActivity.this, CatalogActivity.class);
                                    startActivity(intent);
                                }


                            });


                        } else{
                            runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(), "Unable to add data !", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }


                    }
                    catch (Exception e){
                        Log.i("NewCompanyActivity", "Inside onresponse"+e.getMessage());
                    }
                }


            });


            Log.d("AddNewCompanyActivity", "After api call");
            // sychronous network request


        } catch (JSONException ex) {
           ex.printStackTrace();
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(adapterView.getContext(), text ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}