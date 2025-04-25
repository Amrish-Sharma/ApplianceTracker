package com.cb.appliancetracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.appliancetracker.R;
import com.cb.appliancetracker.model.Appliance;

import java.util.List;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.ApplianceViewHolder> {

    private List<Appliance> appliances;
    private OnApplianceClickListener onApplianceClickListener;

    public ApplianceAdapter(List<Appliance> appliances, OnApplianceClickListener listener) {
        this.appliances = appliances;
        this.onApplianceClickListener = listener;
    }

    @NonNull
    @Override
    public ApplianceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appliance, parent, false);
        return new ApplianceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplianceViewHolder holder, int position) {
        Appliance appliance = appliances.get(position);
        holder.nameTextView.setText(appliance.name);
        holder.warrantyTextView.setText("Warranty Expiry: " + appliance.warrantyExpiry);
        holder.amcTextView.setText("AMC Expiry: " + (appliance.amcExpiry != null ? appliance.amcExpiry : "N/A"));

        holder.itemView.setOnClickListener(v -> onApplianceClickListener.onApplianceClick(appliance));
    }

    @Override
    public int getItemCount() {
        return appliances.size();
    }

    public interface OnApplianceClickListener {
        void onApplianceClick(Appliance appliance);
    }

    public static class ApplianceViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, warrantyTextView, amcTextView;

        public ApplianceViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewApplianceName);
            warrantyTextView = itemView.findViewById(R.id.textViewWarranty);
            amcTextView = itemView.findViewById(R.id.textViewAmc);
        }
    }
}

