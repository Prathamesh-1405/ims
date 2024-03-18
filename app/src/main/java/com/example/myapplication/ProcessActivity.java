package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        findViewById(R.id.stage1TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage1();
                showPopup(R.layout.popup_stage1, R.id.finish_button1);
            }
        });

        findViewById(R.id.stage2TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage2();
                showPopup(R.layout.popup_stage2, R.id.finish_button2);
            }
        });

        findViewById(R.id.stage3TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage3();
                showPopup(R.layout.popup_stage3, R.id.finish_button3);
            }
        });

        findViewById(R.id.stage4TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage4();
                showPopup(R.layout.popup_stage4, R.id.finish_button4);
            }
        });

        findViewById(R.id.stage5TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStage5();
                showPopup(R.layout.popup_stage5, R.id.finish_button5);
            }
        });
    }

//    public void onStageClick(View view) {
//        int stageId = view.getId();
//
//        // Determine which stage was clicked
//        switch (stageId) {
//            case R.id.stage1TextView:
//                setupStage1();
//                showPopup(R.layout.popup_stage1, R.id.finish_button1);
//                break;
//            case R.id.stage2TextView:
//                setupStage2();
//                showPopup(R.layout.popup_stage2, R.id.finish_button2);
//                break;
//
//            case R.id.stage3TextView:
//                setupStage3();
//                Button btn3 = findViewById(R.id.finish_button3);
//                showPopup(R.layout.popup_stage3, R.id.finish_button3);
//                break;
//            case R.id.stage4TextView:
//                setupStage4();
//                showPopup(R.layout.popup_stage4, R.id.finish_button4);
//                break;
//            case R.id.stage5TextView:
//                setupStage5();
//                showPopup(R.layout.popup_stage5, R.id.finish_button5);
//                break;
//        }
//    }

    private void showPopup(int layoutId, int btnId) {
        // Create a dialog to show the popup
        Dialog dialog = new Dialog(this);
        dialog.setContentView(layoutId);
        Window window = dialog.getWindow();
        if(window != null){
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
        }
        dialog.show();
        // Find the finish button in the dialog layout

//        View dialogView = LayoutInflater.from(this).inflate(R.layout.update_order_item_layout,null);
//        Button finishBtn = findViewById(btnId);
//
//        finishBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

    }
    private void setupStage1() {
        // Assume you have retrieved item data from the database
        // For demo, let's say we have an array of item names and total quantities
        String[] itemNames = {"Item 1", "Item 2", "Item 3"};
        int[] totalQuantities = {10, 20, 15};

        LinearLayout container = findViewById(R.id.container_popup);


        // Dynamically add views for each item
        for (int i = 0; i < itemNames.length; i++) {
            View itemView = LayoutInflater.from(ProcessActivity.this).inflate(R.layout.update_order_item_layout, container, false);
            TextView itemName = itemView.findViewById(R.id.item_name);
            TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
            EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);
            itemName.setText(itemNames[i]);
            totalQuantity.setText("Total Qty: " + totalQuantities[i]);
            if(container == null){
                Log.i("ProcessActivity", "container is null");
            }
            if(container != null){
                container.addView(itemView);
            }
        }
    }


    //for stage 2
    private void setupStage2() {
        // Assume you have retrieved item data from the database
        // For demo, let's say we have an array of item names and total quantities
        String[] itemNames = {"Item 1", "Item 2", "Item 3"};
        int[] totalQuantities = {10, 20, 15};

        LinearLayout container = findViewById(R.id.container2);

        // Dynamically add views for each item
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < itemNames.length; i++) {
                    View itemView = LayoutInflater.from(ProcessActivity.this).inflate(R.layout.update_order_item_layout, container, false);
                    TextView itemName = itemView.findViewById(R.id.item_name);
                    TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
                    EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

                    itemName.setText(itemNames[i]);
                    totalQuantity.setText("Total Qty: " + totalQuantities[i]);

                    if(container != null){
                        container.addView(itemView);
                    }
                }
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < itemNames.length; i++) {
                    View itemView = LayoutInflater.from(ProcessActivity.this).inflate(R.layout.update_order_item_layout, container, false);
                    TextView itemName = itemView.findViewById(R.id.item_name);
                    TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
                    EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

                    itemName.setText(itemNames[i]);
                    totalQuantity.setText("Total Qty: " + totalQuantities[i]);
                    if(container != null){
                        container.addView(itemView);
                    }
                }
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < itemNames.length; i++) {
                    View itemView = LayoutInflater.from(ProcessActivity.this).inflate(R.layout.update_order_item_layout, container, false);
                    TextView itemName = itemView.findViewById(R.id.item_name);
                    TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
                    EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

                    itemName.setText(itemNames[i]);
                    totalQuantity.setText("Total Qty: " + totalQuantities[i]);
                    if(container != null){
                        container.addView(itemView);
                    }
                }
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < itemNames.length; i++) {
                    View itemView = LayoutInflater.from(ProcessActivity.this).inflate(R.layout.update_order_item_layout, container, false);
                    TextView itemName = itemView.findViewById(R.id.item_name);
                    TextView totalQuantity = itemView.findViewById(R.id.total_quantity);
                    EditText completedQuantity = itemView.findViewById(R.id.completed_quantity);

                    itemName.setText(itemNames[i]);
                    totalQuantity.setText("Total Qty: " + totalQuantities[i]);
                    if(container != null){
                        container.addView(itemView);
                    }
                }

            }
        });


    }

}