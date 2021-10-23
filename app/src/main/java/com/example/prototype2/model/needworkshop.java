package com.example.prototype2.model;

public class needworkshop {
    private String NamaLengkap;
    private String Keterangan;
    private String NoHP;
    private String JenisKendaraan;
    private String AlamatDetail;
    private String Kondisi;
    private String Longitude;
    private String Latitude;

    public needworkshop(){}

    public needworkshop(String namaLengkap, String keterangan, String noHP, String jenisKendaraan, String alamatDetail, String kondisi, String longitude, String latitude) {
        NamaLengkap = namaLengkap;
        Keterangan = keterangan;
        NoHP = noHP;
        JenisKendaraan = jenisKendaraan;
        AlamatDetail = alamatDetail;
        Kondisi = kondisi;
        Longitude = longitude;
        Latitude = latitude;
    }

    public String getNamaLengkap() {
        return NamaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        NamaLengkap = namaLengkap;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getNoHP() {
        return NoHP;
    }

    public void setNoHP(String noHP) {
        NoHP = noHP;
    }

    public String getJenisKendaraan() {
        return JenisKendaraan;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        JenisKendaraan = jenisKendaraan;
    }

    public String getAlamatDetail() {
        return AlamatDetail;
    }

    public void setAlamatDetail(String alamatDetail) {
        AlamatDetail = alamatDetail;
    }

    public String getKondisi() {
        return Kondisi;
    }

    public void setKondisi(String kondisi) {
        Kondisi = kondisi;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

}
