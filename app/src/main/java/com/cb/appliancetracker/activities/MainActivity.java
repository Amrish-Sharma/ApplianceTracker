package com.cb.appliancetracker.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.appliancetracker.R;
import com.cb.appliancetracker.database.ApplianceDatabase;
import com.cb.appliancetracker.model.Appliance;
import com.cb.appliancetracker.adapter.ApplianceAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApplianceAdapter applianceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set your actual layout file here
        setContentView(R.layout.activity_appliance_list);  // Replace with your layout file

        recyclerView = findViewById(R.id.recyclerViewAppliances);  // Ensure your layout has a RecyclerView with this ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        applianceAdapter = new ApplianceAdapter(null, new ApplianceAdapter.OnApplianceClickListener() {
            @Override
            public void onApplianceClick(Appliance appliance) {
                // Handle appliance click (e.g., show a toast or open a detail screen)
                Toast.makeText(MainActivity.this, "Clicked: " + appliance.getName(), Toast.LENGTH_SHORT).show();
            }
        });
            recyclerView.setAdapter(applianceAdapter);  // Set the adapter to the RecyclerView
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAppliances(); // Call your method to reload list
    }

    private void loadAppliances() {
        new Thread(() -> {
            try {
                List<Appliance> applianceList = ApplianceDatabase.getInstance(this).applianceDao().getAllAppliances();
                runOnUiThread(() -> {
                    if (applianceAdapter != null) {
                        applianceAdapter.setAppliances(applianceList); // Update adapter
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    // Handle error, maybe show a toast or error message
                });
            }
        }).start();
    }
}
