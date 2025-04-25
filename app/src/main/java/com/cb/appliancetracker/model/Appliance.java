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
}
