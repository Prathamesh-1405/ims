package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Admin extends AppCompatActivity {

    Button add;
    AlertDialog dialog;
    LinearLayout layout;

    OkHttpClient client;

    int arrLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);
        client = new OkHttpClient();
        ApiServiceDetails apiServiceDetails = null;
        String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/users";
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
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
                        JSONArray users = (JSONArray) jsonRes.get("Users");

                        arrLength = users.length();
                        for(int i = 0; i < arrLength; i++){
                            JSONObject temp = users.getJSONObject(i);
                            addCard(temp.getString("name"), temp.getString("username"), "");
                        }

                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
//      getting add button
        add = findViewById(R.id.add);
//        getting linear layout
        layout = findViewById(R.id.container);
        buildDialog();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        final EditText name = view.findViewById(R.id.nameEdit);
        final EditText username = view.findViewById(R.id.usernameDialogEdit);  // Added username edit text
        final EditText password = view.findViewById(R.id.passwordDialogEdit);  // Added password edit text
        final EditText confirmPassword = view.findViewById(R.id.confirmPasswordDialogEdit);  // Added confirm password edit text

        // Add validations to check if username and password are empty and confirm password matches password

        builder.setView(view);
        builder.setTitle("Register User")
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String enteredName = name.getText().toString();
                        String enteredUsername = username.getText().toString();  // Get username
                        String enteredPassword = password.getText().toString();  // Get password
                        String enteredConfirmPassword = confirmPassword.getText().toString();  // Get confirm password

                        // Add validation checks here (e.g., username and password emptiness, password match)
                        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                            Toast.makeText(Admin.this, "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
                        } else if (!enteredPassword.equals(enteredConfirmPassword)) {
                            Toast.makeText(Admin.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        } else {
                            // api post request
                            OkHttpClient okHttpClient = new OkHttpClient();
                            ApiServiceDetails apiServiceDetails = null;
                            String url = apiServiceDetails.protocol + "://" + apiServiceDetails.host + ":" + apiServiceDetails.port + "/register";



                            JSONObject jsonObject  = new JSONObject();
                            try {
                                jsonObject.put("name", name.getText().toString());
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
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if(response.isSuccessful()){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "User added !", Toast.LENGTH_SHORT).show();
                                                addCard(enteredName, enteredUsername, enteredPassword);
                                            }
                                        });
                                    }
                                    if(response.code() == 409){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "User already exists !", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }



    private void addCard(String name, String username, String password) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);

        TextView nameView = view.findViewById(R.id.name);
        final ImageView delete = view.findViewById(R.id.delete);
        final ImageView edit =  view.findViewById(R.id.edit);
        final ImageView info = view.findViewById(R.id.info);

        nameView.setText(name);

        // Store username securely (e.g., encrypted hash)
        HashMap<String, String> userDetails = new HashMap<>();
        userDetails.put("name", name);
        userDetails.put("username", username);

        // *DO NOT STORE PLAINTEXT PASSWORD!*

        view.setTag(userDetails);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit action here (consider passing user details to edit functionality)
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user details from the view tag
                HashMap<String, String> details = (HashMap<String, String>) view.getTag();
                String userName = details.get("username");

                // Build and show a dialog with user details (excluding password)
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
                builder.setTitle("User Details");

                String infoText = "Name: " + details.get("name") + "\nUsername: " + userName + "\n\n*Password cannot be displayed due to security reasons.*";
                builder.setMessage(infoText);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });

        layout.addView(view);
    }

}