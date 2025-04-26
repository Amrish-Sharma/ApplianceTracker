package com.cb.appliancetracker.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cb.appliancetracker.model.Appliance;

import java.util.List;

@Dao
public interface ApplianceDao {

    @Insert
    long insert(Appliance appliance);

    @Update
    void update(Appliance appliance);

    @Delete
    void delete(Appliance appliance);

    @Query("SELECT * FROM appliances ORDER BY warrantyExpiry ASC")
    List<Appliance> getAllAppliances();

    @Query("SELECT * FROM appliances WHERE id = :id")
    Appliance getApplianceById(int id);

    @Insert
    void insertAppliance(Appliance appliance);
}
