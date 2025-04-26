package com.cb.appliancetracker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
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

    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private ApplianceAdapter applianceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_list);

        addButton = findViewById(R.id.fabAdd); // ✅ Now safe
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
            startActivity(intent);
        });

        new Thread(() -> {
            Context appContext = getApplicationContext();
            List<Appliance> fetchedAppliances = ApplianceDatabase.getInstance(appContext)
                    .applianceDao()
                    .getAllAppliances();

            runOnUiThread(() -> applianceAdapter.updateAppliances(fetchedAppliances));
        }).start();
    }
}

