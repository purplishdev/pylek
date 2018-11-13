package me.michalwozniak.pylek.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Station;

@Dao
public interface AirDataDao {

    @Query("SELECT * FROM AirData WHERE station = :station")
    Single<List<AirData>> getAll(Station station);

    @Insert
    void insertAll(AirData... airData);
}
