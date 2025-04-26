package com.cb.appliancetracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cb.appliancetracker.R;
import com.cb.appliancetracker.database.ApplianceDatabase;
import com.cb.appliancetracker.model.Appliance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddApplianceActivity extends AppCompatActivity {

    private EditText editTextName, editTextPurchaseDate, editTextWarrantyDate, editTextAmcDate, editTextNotes;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appliance); // 👈 Must come first

        // Now safe to find views
        editTextName = findViewById(R.id.editTextName);
        editTextPurchaseDate = findViewById(R.id.editTextPurchaseDate);
        editTextWarrantyDate = findViewById(R.id.editTextWarrantyDate);
        editTextAmcDate = findViewById(R.id.editTextAmcDate);
        editTextNotes = findViewById(R.id.editTextNotes);
        addButton = findViewById(R.id.buttonAdd);

        addButton.setOnClickListener(v -> {
            Appliance appliance = new Appliance();
            appliance.name = editTextName.getText().toString();
            appliance.purchaseDate = Long.parseLong(editTextPurchaseDate.getText().toString());
            appliance.warrantyExpiry = Long.parseLong(editTextWarrantyDate.getText().toString());
            appliance.amcExpiry = Long.parseLong(editTextAmcDate.getText().toString());
            appliance.notes = editTextNotes.getText().toString();

            new Thread(() -> {
                ApplianceDatabase.getInstance(this).applianceDao().insertAppliance(appliance);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Appliance added!", Toast.LENGTH_SHORT).show();
                    finish(); // Back to list
                });
            }).start();
        });

    }
}

