package com.cb.appliancetracker.activities;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cb.appliancetracker.R;
import com.cb.appliancetracker.database.ApplianceDatabase;
import com.cb.appliancetracker.model.Appliance;

public class ApplianceDetailActivity extends AppCompatActivity {

    private TextView textViewName, textViewPurchaseDate, textViewWarrantyDate, textViewAmcDate, textViewNotes;
    private ImageView imageViewReceipt;
    private int applianceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_detail);

        textViewName = findViewById(R.id.textViewApplianceName);
        textViewPurchaseDate = findViewById(R.id.textViewPurchaseDate);
        textViewWarrantyDate = findViewById(R.id.textViewWarrantyDate);
        textViewAmcDate = findViewById(R.id.textViewAmcDate);
        textViewNotes = findViewById(R.id.textViewNotes);
        imageViewReceipt = findViewById(R.id.imageViewReceipt);

        applianceId = getIntent().getIntExtra("appliance_id", -1);

        if (applianceId == -1) {
            Toast.makeText(this, "Invalid appliance ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch appliance details from the database
        new Thread(() -> {
            Appliance appliance = ApplianceDatabase.getInstance(this).applianceDao().getApplianceById(applianceId);
            runOnUiThread(() -> {
                if (appliance != null) {
                    textViewName.setText(appliance.name);
                    textViewPurchaseDate.setText("Purchase Date: " + appliance.purchaseDate);
                    textViewWarrantyDate.setText("Warranty Expiry: " + appliance.warrantyExpiry);
                    textViewAmcDate.setText("AMC Expiry: " + (appliance.amcExpiry != null ? appliance.amcExpiry : "N/A"));
                    textViewNotes.setText("Notes: " + appliance.notes);

                    // Load the receipt image (if available)
                    if (appliance.imageUri != null) {
                        // Code to load the image from URI (e.g., using Glide or Picasso)
                        // Glide.with(this).load(Uri.parse(appliance.imageUri)).into(imageViewReceipt);
                    }
                } else {
                    Toast.makeText(this, "Appliance not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();


    }
}
