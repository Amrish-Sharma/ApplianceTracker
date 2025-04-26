package com.cb.appliancetracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.appliancetracker.R;
import com.cb.appliancetracker.adapter.ApplianceAdapter;
import com.cb.appliancetracker.database.ApplianceDatabase;
import com.cb.appliancetracker.model.Appliance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ApplianceListActivity extends AppCompatActivity {

    private static final int ADD_APPLIANCE_REQUEST_CODE = 1;

    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private ApplianceAdapter applianceAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_APPLIANCE_REQUEST_CODE && resultCode == RESULT_OK) {
            refreshApplianceList(); // Reload the data from the database
        }
    }

    private void refreshApplianceList() {
        new Thread(() -> {
            List<Appliance> appliances = ApplianceDatabase.getInstance(this).applianceDao().getAllAppliances();
            System.out.println("Fetched appliances: " + appliances.size()); // Log the size of the list
            runOnUiThread(() -> {
                if (applianceAdapter != null) {
                    applianceAdapter.setAppliances(appliances); // Update the adapter with new data
                }
            });
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_list);

        addButton = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.recyclerViewAppliances);

        List<Appliance> appliances = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        applianceAdapter = new ApplianceAdapter(appliances, appliance -> {
            Intent intent = new Intent(ApplianceListActivity.this, ApplianceDetailActivity.class);
            intent.putExtra("appliance_id", appliance.id);
            startActivity(intent);
        });

        recyclerView.setAdapter(applianceAdapter);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(ApplianceListActivity.this, AddApplianceActivity.class);
            startActivityForResult(intent, ADD_APPLIANCE_REQUEST_CODE); // Use startActivityForResult
        });

        refreshApplianceList(); // Load the initial list of appliances
    }
}