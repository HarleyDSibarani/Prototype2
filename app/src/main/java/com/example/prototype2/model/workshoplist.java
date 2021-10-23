package com.example.prototype2.model;

public class workshoplist {
    private String NamaBengkel;
    private String Keterangan;
    private String NoHP;
    private String JenisBengkel;
    private String AlamatDetail;
    private String Longitude;
    private String Latitude;

    public workshoplist(){}

    public String getNamaBengkel() {
        return NamaBengkel;
    }

    public void setNamaBengkel(String namaBengkel) {
        NamaBengkel = namaBengkel;
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

    public String getJenisBengkel() {
        return JenisBengkel;
    }

    public void setJenisBengkel(String jenisBengkel) {
        JenisBengkel = jenisBengkel;
    }

    public String getAlamatDetail() {
        return AlamatDetail;
    }

    public void setAlamatDetail(String alamatDetail) {
        AlamatDetail = alamatDetail;
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



    public workshoplist(String namaBengkel, String keterangan, String noHP, String jenisBengkel, String alamatDetail, String longitude, String latitude) {
        NamaBengkel = namaBengkel;
        Keterangan = keterangan;
        NoHP = noHP;
        JenisBengkel = jenisBengkel;
        AlamatDetail = alamatDetail;
        Longitude = longitude;
        Latitude = latitude;
    }


}
