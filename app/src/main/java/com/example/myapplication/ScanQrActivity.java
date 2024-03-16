package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    private SurfaceView cameraPreview;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        cameraPreview = findViewById(R.id.camera_preview);
        scannerView = new ZXingScannerView(this);

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
        SurfaceHolder surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (scannerView != null) {
                        scannerView.setResultHandler(ScanQrActivity.this);
                        scannerView.startCamera();
                        setContentView(scannerView);
                    }
                } catch (Exception e) {
                    Log.e("Scanner", "Error starting camera: " + e.getMessage());
                    Toast.makeText(ScanQrActivity.this, "Failed to start camera", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // Not used, but required to implement SurfaceHolder.Callback
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // Release camera resources
                if (scannerView != null) {
                    scannerView.stopCamera();
                }
            }
        });
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

    @Override
    public void handleResult(Result result) {
        // Handle QR code result
        Toast.makeText(this, "QR Code Scanned: " + result.getText(), Toast.LENGTH_SHORT).show();

        // Resume scanning after a short delay
        scannerView.resumeCameraPreview(this);
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