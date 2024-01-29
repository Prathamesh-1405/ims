package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.net.wifi.hotspot2.ConfigParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewCompanyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button addCompanyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_company);
        addCompanyBtn = findViewById(R.id.addCompanyBtn);

        Spinner spinner1 = findViewById(R.id.stateField);
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
                Spinner stateSelectMenu = findViewById(R.id.stateField);
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
                String state = stateSelectMenu.getSelectedItem().toString();
                String gstNo = gstNoField.getText().toString();
                String companyInSez = companyInSezField.getSelectedItem().toString();
//                if(companyInSezField.getSelectedItem().toString() == "Yes"){
//                    companyInSez = true;
//                }else{
//                    companyInSez = false;
//                }
                String companyType = companyTypeField.getSelectedItem().toString();
                String supplierType = supplierTypeField.getSelectedItem().toString();
                Float distanceFromAndheri = Float.parseFloat(distanceFromAndheriField.getText().toString());
                Float distanceFromVasai = Float.parseFloat(distanceFromVasaiField.getText().toString());
                Log.d("AddNewCompanyActivity","Data collected");
                postData(companyName, address, city, pinCode, state, gstNo, companyInSez, companyType, supplierType, distanceFromAndheri, distanceFromVasai);


            }
        });
    }

    private void postData(String name, String address, String city, String pincode, String state, String gstNo, String companyInSez, String companyType, String supplierType, Float distanceFromAndheri, Float distanceFromVasai) {
        // below line is for displaying our progress bar.

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPICall.URL_BASE)
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPICall retrofitAPI = retrofit.create(RetrofitAPICall.class);

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
            Call<String> apiCall = retrofitAPI.addCompany(paramObject.toString());

//            apiCall.execute();

//            async function call
            Log.d("AddNewCompanyActivity","Before api call");
            apiCall.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Toast.makeText(getApplicationContext(), response.headers().toString() , Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), response.body().toString() , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Failed to add data !", Toast.LENGTH_SHORT).show();

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}