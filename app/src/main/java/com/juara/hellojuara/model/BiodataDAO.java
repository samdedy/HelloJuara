package com.juara.hellojuara.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BiodataDAO {
    @Query("SELECT * FROM Biodata")
    List<Biodata> getAll();

    @Query("SELECT * FROM Biodata WHERE nama LIKE '%' || :nama || '%'")
    List<Biodata> findByNama(String nama);

    @Query("SELECT * FROM Biodata WHERE telepon = :telepon")
    Biodata findByTelepon(String telepon);

    @Insert
    void insertAll(Biodata... users);

    @Delete
    void delete(Biodata user);
}
