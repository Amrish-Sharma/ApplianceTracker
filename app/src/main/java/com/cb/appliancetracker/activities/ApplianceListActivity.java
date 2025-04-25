package com.cb.appliancetracker.activities;

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

import java.util.List;

public class ApplianceListActivity extends AppCompatActivity {

    private Button addButton;
    private RecyclerView recyclerView;
    private ApplianceAdapter applianceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_list);

        addButton = findViewById(R.id.add_button);

        recyclerView = findViewById(R.id.recyclerViewAppliances);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the onClickListener for the Add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ApplianceDetailActivity to add a new appliance
                Intent intent = new Intent(ApplianceListActivity.this, ApplianceDetailActivity.class);
                startActivity(intent);
            }
        });
        // Fetch appliance data in a background thread
        new Thread(() -> {
            List<Appliance> appliances = ApplianceDatabase.getInstance(this).applianceDao().getAllAppliances();
            runOnUiThread(() -> {
                applianceAdapter = new ApplianceAdapter(appliances, appliance -> {
                    // Navigate to appliance detail activity
                    Intent intent = new Intent(ApplianceListActivity.this, ApplianceDetailActivity.class);
                    intent.putExtra("appliance_id", appliance.id);
                    startActivity(intent);
                });
                recyclerView.setAdapter(applianceAdapter);
            });
        }).start();
    }
}
