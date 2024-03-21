package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;


public class ScanQrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    private ZXingScannerView scannerView;
    private TextView scannedInfoTextView;
    private SurfaceView cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        cameraPreview = findViewById(R.id.camera_preview);
        scannedInfoTextView = findViewById(R.id.scannedInfoTextView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    private void startCamera() {
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(this);

        SurfaceHolder surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    scannerView.startCamera();
                    scannerView.resumeCameraPreview(ScanQrActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ScanQrActivity.this, "Failed to start camera", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (scannerView != null) {
                    scannerView.stopCamera();
                }
            }
        });

        // Add scannerView to the layout
        addContentView(scannerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission is required for scanning", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

//    @Override
//    public void handleResult(Result result) {
//        String scannedInfo = result.getText();
//        Intent intent = new Intent(this, ScannedInfoActivity.class);
//        intent.putExtra("SCANNED_INFO", scannedInfo);
//        startActivity(intent);
//    }

    @Override
    public void handleResult(Result result) {
        String scannedInfo = result.getText();

        // Check if the scanned info contains a link
        if (isLink(scannedInfo)) {
            // Open the link in the browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedInfo));
            startActivity(browserIntent);
        } else {
            // If not a link, proceed to ScannedInfoActivity
            Intent intent = new Intent(this, ScannedInfoActivity.class);
            intent.putExtra("SCANNED_INFO", scannedInfo);
            startActivity(intent);
        }
    }

    private boolean isLink(String text) {
        // A simple check to see if the text contains "http://" or "https://"
        return text != null && (text.startsWith("http://") || text.startsWith("https://"));
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (scannerView != null) {
            scannerView.stopCamera();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestCameraPermission();
        }
    }
}
