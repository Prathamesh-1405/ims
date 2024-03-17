package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProcessActivity extends AppCompatActivity {

    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
    }

    public void onStageClick(View view) {
        int stageId = view.getId();

        // Determine which stage was clicked
        switch (stageId) {
            case R.id.stage1TextView:
                showPopup(R.layout.popup_stage1);
                setupStage1();
                break;
            case R.id.stage2TextView:
                showPopup(R.layout.popup_stage2);
                break;
            // Add cases for other stages similarly
        }
    }

    private void showPopup(int layoutId) {
        // Create a dialog to show the popup
        Dialog dialog = new Dialog(this);
        dialog.setContentView(layoutId);
        dialog.show();
    }

    private void setupStage1() {
        // Assume you have retrieved item data from the database
        // For demo, let's say we have an array of item names and total quantities
        String[] itemNames = {"Item 1", "Item 2", "Item 3"};
        int[] totalQuantities = {10, 20, 15};

        container = findViewById(R.id.container);


        // Dynamically add views for each item
        for (int i = 0; i < itemNames.length; i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.update_order_item_layout, container, false);
            TextView itemName = itemView.findViewById(R.id.item_name);
            TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
            EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

            itemName.setText(itemNames[i]);
            totalQuantity.setText("Total Qty: " + totalQuantities[i]);

            container.addView(itemView);
        }

        Button finishButton = findViewById(R.id.finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the popup
                finish();
            }
        });
    }


    //for stage 2
    private void setupStage2() {
        // Assume you have retrieved item data from the database
        // For demo, let's say we have an array of item names and total quantities
        String[] itemNames = {"Item 1", "Item 2", "Item 3"};
        int[] totalQuantities = {10, 20, 15};

        LinearLayout container = findViewById(R.id.container2);

        // Dynamically add views for each item
        for (int i = 0; i < itemNames.length; i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.update_order_item_layout, container, false);
            TextView itemName = itemView.findViewById(R.id.item_name);
            TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
            EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

            itemName.setText(itemNames[i]);
            totalQuantity.setText("Total Qty: " + totalQuantities[i]);

            container.addView(itemView);
        }

        Button finishButton = findViewById(R.id.finish_button2);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the popup
                finish();
            }
        });
    }

    //for stage 3

    private void setupStage3() {
        // Assume you have retrieved item data from the database
        // For demo, let's say we have an array of item names and total quantities
        String[] itemNames = {"Item 1", "Item 2", "Item 3"};
        int[] totalQuantities = {10, 20, 15};

        LinearLayout container = findViewById(R.id.container3);

        // Dynamically add views for each item
        for (int i = 0; i < itemNames.length; i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.update_order_item_layout, container, false);
            TextView itemName = itemView.findViewById(R.id.item_name);
            TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
            EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

            itemName.setText(itemNames[i]);
            totalQuantity.setText("Total Qty: " + totalQuantities[i]);

            container.addView(itemView);
        }

        Button finishButton = findViewById(R.id.finish_button3);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the popup
                finish();
            }
        });
    }

    //for stage 4

    private void setupStage4() {
        // Assume you have retrieved item data from the database
        // For demo, let's say we have an array of item names and total quantities
        String[] itemNames = {"Item 1", "Item 2", "Item 3"};
        int[] totalQuantities = {10, 20, 15};

        LinearLayout container = findViewById(R.id.container4);

        // Dynamically add views for each item
        for (int i = 0; i < itemNames.length; i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.update_order_item_layout, container, false);
            TextView itemName = itemView.findViewById(R.id.item_name);
            TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
            EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

            itemName.setText(itemNames[i]);
            totalQuantity.setText("Total Qty: " + totalQuantities[i]);

            container.addView(itemView);
        }

        Button finishButton = findViewById(R.id.finish_button4);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the popup
                finish();
            }
        });
    }

    //for stage 5

    private void setupStage5() {
        // Assume you have retrieved item data from the database
        // For demo, let's say we have an array of item names and total quantities
        String[] itemNames = {"Item 1", "Item 2", "Item 3"};
        int[] totalQuantities = {10, 20, 15};

        LinearLayout container = findViewById(R.id.container5);

        // Dynamically add views for each item
        for (int i = 0; i < itemNames.length; i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.update_order_item_layout, container, false);
            TextView itemName = itemView.findViewById(R.id.item_name);
            TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
            EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

            itemName.setText(itemNames[i]);
            totalQuantity.setText("Total Qty: " + totalQuantities[i]);

            container.addView(itemView);
        }

        Button finishButton = findViewById(R.id.finish_button5);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the popup
                finish();
            }
        });
    }
}
