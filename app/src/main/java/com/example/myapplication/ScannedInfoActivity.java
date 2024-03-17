package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScannedInfoActivity extends AppCompatActivity {

    private TextView scannedInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_info);

        // Initialize TextView
        scannedInfoTextView = findViewById(R.id.scannedInfoTextView);

        // Get the scanned information from the intent
        String scannedInfo = getIntent().getStringExtra("SCANNED_INFO");

        // Display the scanned information
        scannedInfoTextView.setText(scannedInfo);
    }
}
