package com.example.prototype2.model;

public class needhelp {
    private String NamaLengkap;
    private String Keterangan;
    private String NoHP;
    private String Email;
    private String AlamatDetail;
    private String Kondisi;
    private String Longitude;
    private String Latitude;

    public needhelp(String IDhelp) {
        this.IDhelp = IDhelp;
    }

    public String getIDhelp() {
        return IDhelp;
    }

    public void setIDhelp(String IDhelp) {
        this.IDhelp = IDhelp;
    }

    private String IDhelp;
    private int Jarak;

    public needhelp(int jarak) {
        Jarak = jarak;
    }

    public int getJarak() {
        return Jarak;
    }

    public void setJarak(int jarak) {
        Jarak = jarak;
    }

    public needhelp(){}

    public needhelp(String namaLengkap, String keterangan, String noHP, String email, String alamatdetail,String kondisi, String longitude, String latitude) {
        NamaLengkap = namaLengkap;
        Keterangan = keterangan;
        NoHP = noHP;
        Email = email;
        AlamatDetail = alamatdetail;
        Kondisi = kondisi;
        Longitude = longitude;
        Latitude = latitude;
    }

    public String getNoHP() {
        return NoHP;
    }

    public void setNoHP(String noHP) {
        NoHP = noHP;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getAlamatdetail() {
        return AlamatDetail;
    }

    public void setAlamatdetail(String alamatdetail) {
        this.AlamatDetail = alamatdetail;
    }

    public String getNamaLengkap() {
        return NamaLengkap;
    }

    public void setNamaLengkap(String NamaLengkap) {
        this.NamaLengkap = NamaLengkap;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getKondisi() {
        return Kondisi;
    }

    public void setKondisi(String kondisi) {
        this.Kondisi = kondisi;
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
