package com.cb.appliancetracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cb.appliancetracker.model.Appliance;

@Database(entities = {Appliance.class}, version = 1)
public abstract class ApplianceDatabase extends RoomDatabase {

    private static ApplianceDatabase instance;

    public abstract ApplianceDao applianceDao();

    public static synchronized ApplianceDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ApplianceDatabase.class,
                    "appliance_database"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}

