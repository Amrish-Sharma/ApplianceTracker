package com.cb.appliancetracker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appliances")
public class Appliance {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public long purchaseDate;
    public long warrantyExpiry;
    public Long amcExpiry; // nullable
    public String notes;
    public String imageUri; // for receipt image

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public long getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public void setWarrantyExpiry(long warrantyExpiry) {
        this.warrantyExpiry = warrantyExpiry;
    }

    public Long getAmcExpiry() {
        return amcExpiry;
    }

    public void setAmcExpiry(Long amcExpiry) {
        this.amcExpiry = amcExpiry;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
