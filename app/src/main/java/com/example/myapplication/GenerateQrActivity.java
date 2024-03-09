package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.os.Bundle;

public class GenerateQrActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        qrCodeImageView = findViewById(R.id.qr_code);

        // Getting the inputs from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String orderNo = intent.getStringExtra("orderNo");
            String cuttingSize = intent.getStringExtra("cuttingSize");
            String cuttingWeight = intent.getStringExtra("cuttingWeight");

            // Generating QR code based on the inputs
            generateQRCode(orderNo, cuttingSize, cuttingWeight);
        }
    }

    private void generateQRCode(String orderNo, String cuttingSize, String cuttingWeight) {
        String qrContent = "Order No: " + orderNo + "\nCutting Size: " + cuttingSize + "\nCutting Weight: " + cuttingWeight;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
        }
    }
}