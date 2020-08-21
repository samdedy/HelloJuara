package com.juara.hellojuara.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Biodata")
public class Biodata {

    @ColumnInfo(name = "nama")
    private String nama;
    @ColumnInfo(name = "jenis_kelamin")
    private String jenis_kelamin;
    @ColumnInfo(name = "pekerjaan")
    private String pekerjaan;
    @ColumnInfo(name = "tanggal_lahir")
    private String tanggal_lahir;
    @ColumnInfo(name = "alamat")
    private String alamat;
    @PrimaryKey
    @NonNull
    private String telepon;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "catatan")
    private String catatan;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
