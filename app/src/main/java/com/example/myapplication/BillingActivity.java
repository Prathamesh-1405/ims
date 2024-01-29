package com.example.myapplication;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class BillingActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_billing);
//    }
//}


//**********************************
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BillingActivity extends AppCompatActivity {

    public static void generatePdf(Context context, String userInput1, String userInput2) {
        // Create a new PdfDocument
        PdfDocument document = new PdfDocument();

        // Create a PageInfo
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();

        // Start a new page
        PdfDocument.Page page = document.startPage(pageInfo);

        // Get the canvas for drawing
        Canvas canvas = page.getCanvas();

        // Create a paint object for styling
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);

        // Draw text on the canvas
        canvas.drawText("User Input 1: " + userInput1, 10, 50, paint);
        canvas.drawText("User Input 2: " + userInput2, 10, 70, paint);

        // Finish the page
        document.finishPage(page);

        // Save the document to a file
        String fileName = "UserInputs.pdf";
        File filePath = new File(Environment.getExternalStorageDirectory(), fileName);

        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(context, "PDF generated successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error generating PDF", Toast.LENGTH_SHORT).show();
        }

        // Close the document
        document.close();
    }
}
