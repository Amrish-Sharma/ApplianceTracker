package com.cb.appliancetracker.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Date;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;

import com.cb.appliancetracker.R;
import com.cb.appliancetracker.database.ApplianceDatabase;
import com.cb.appliancetracker.model.Appliance;

import java.text.SimpleDateFormat;

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
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                Appliance appliance = new Appliance();
                appliance.name = editTextName.getText().toString();
                appliance.purchaseDate = sdf.parse(editTextPurchaseDate.getText().toString()).getTime(); // Convert date string to timestamp
                appliance.warrantyExpiry = sdf.parse(editTextWarrantyDate.getText().toString()).getTime(); // Same here

                String amcDateText = editTextAmcDate.getText().toString();
                if (!amcDateText.isEmpty()) {
                    appliance.amcExpiry = sdf.parse(amcDateText).getTime();
                } else {
                    appliance.amcExpiry = 0L; // optional field
                }

                appliance.notes = editTextNotes.getText().toString();

                new Thread(() -> {
                    ApplianceDatabase.getInstance(this).applianceDao().insertAppliance(appliance);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Appliance added!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();

                    });
                }).start();

            } catch (Exception e) {
                Toast.makeText(this, "Invalid date format!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }
}

