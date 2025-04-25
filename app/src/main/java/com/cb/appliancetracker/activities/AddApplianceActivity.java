package com.cb.appliancetracker.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cb.appliancetracker.R;
import com.cb.appliancetracker.database.ApplianceDatabase;
import com.cb.appliancetracker.model.Appliance;

import java.util.Calendar;

public class AddApplianceActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;

    EditText editTextName, editTextNotes;
    Button buttonPickPurchaseDate, buttonPickWarrantyDate, buttonPickAmcDate, buttonSelectImage, buttonSave;

    long purchaseDate = 0;
    long warrantyDate = 0;
    Long amcDate = null;
    Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appliance);

        editTextName = findViewById(R.id.editTextName);
        editTextNotes = findViewById(R.id.editTextNotes);

        buttonPickPurchaseDate = findViewById(R.id.buttonPickPurchaseDate);
        buttonPickWarrantyDate = findViewById(R.id.buttonPickWarrantyDate);
        buttonPickAmcDate = findViewById(R.id.buttonPickAmcDate);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonSave = findViewById(R.id.buttonSave);

        buttonPickPurchaseDate.setOnClickListener(v -> showDatePicker(date -> purchaseDate = date));
        buttonPickWarrantyDate.setOnClickListener(v -> showDatePicker(date -> warrantyDate = date));
        buttonPickAmcDate.setOnClickListener(v -> showDatePicker(date -> amcDate = date));

        buttonSelectImage.setOnClickListener(v -> openImagePicker());

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String notes = editTextNotes.getText().toString().trim();

            if (name.isEmpty() || purchaseDate == 0 || warrantyDate == 0) {
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Appliance appliance = new Appliance();
            appliance.name = name;
            appliance.purchaseDate = purchaseDate;
            appliance.warrantyExpiry = warrantyDate;
            appliance.amcExpiry = amcDate;
            appliance.notes = notes;
            appliance.imageUri = selectedImageUri != null ? selectedImageUri.toString() : null;

            new Thread(() -> {
                ApplianceDatabase.getInstance(this).applianceDao().insert(appliance);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Appliance saved!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }

    private interface OnDateSelectedListener {
        void onDateSelected(long millis);
    }

    private void showDatePicker(OnDateSelectedListener listener) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            calendar.set(year, month, day, 0, 0);
            listener.onDateSelected(calendar.getTimeInMillis());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        }
    }
}
