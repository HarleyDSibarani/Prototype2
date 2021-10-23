package com.example.prototype2.model;

public class SOS {
    private String Longitude;
    private String Latitude;
    private String NamaLengkap;
    private String NoHP;

    public SOS(){}


    public SOS(String longitude, String latitude, String namaLengkap, String noHP) {
        Longitude = longitude;
        Latitude = latitude;
        NamaLengkap = namaLengkap;
        NoHP = noHP;
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

    public String getNamaLengkap() {
        return NamaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        NamaLengkap = namaLengkap;
    }

    public String getNoHP() {
        return NoHP;
    }

    public void setNoHP(String noHP) {
        NoHP = noHP;
    }

}