package me.michalwozniak.pylek.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import me.michalwozniak.pylek.model.AirData;

@Database(entities = {AirData.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AirDataDao airDataDao();
}
