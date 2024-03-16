package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class GenerateQrActivity extends AppCompatActivity {

    private LinearLayout save;
    private LinearLayout share;
    private LinearLayout cancel;
    private Bitmap qrCodeBitmap;
    private ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        save = findViewById(R.id.Save);
        share = findViewById(R.id.Share);
        cancel = findViewById(R.id.Cancel);
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

        // Set click listener to LinearLayout
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageLocally(qrCodeBitmap);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(qrCodeBitmap);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GenerateQrActivity.this, HomeActivity.class));
            }
        });

    }

    private void generateQRCode(String orderNo, String cuttingSize, String cuttingWeight) {
        String qrContent = "Order No: " + orderNo + "\nCutting Size: " + cuttingSize + "\nCutting Weight: " + cuttingWeight;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            qrCodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrCodeBitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrCodeImageView.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImageLocally(Bitmap bitmap) {
        // Get external storage directory
        String folderPath = getExternalFilesDir(null).getAbsolutePath();

        String fileName = "QR_Code.jpg"; // Name for your image file

        File file = new File(folderPath, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); // Compress bitmap into JPEG format
            fos.flush();
            fos.close();
            Toast.makeText(this, "Image saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareImage(Bitmap bitmap) {
        // Save the bitmap to a temporary file
        if (qrCodeBitmap != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/png");
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(qrCodeBitmap));
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share QR Code"));
        }
    }

    private Uri getImageUri(Bitmap inImage) {
        String path = Objects.requireNonNull(getExternalCacheDir()).getPath() + "/qr_code.png";
        java.io.File file = new java.io.File(path);
        try {
            java.io.FileOutputStream out = new java.io.FileOutputStream(file);
            inImage.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return android.net.Uri.parse("file://" + file.getAbsolutePath());
    }
}
