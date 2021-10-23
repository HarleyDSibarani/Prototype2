package com.example.prototype2.model;

public class User {

    private String id;
    private String fullname;
    private String nohp;
    private String email;
    private String role;
    private String foto;

    public User(String id, String fullname, String email, String nohp, String role, String foto) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.nohp = nohp;
        this.role = role;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getnohp() {
        return nohp;
    }

    public void setnohp(String nohp) {
        this.nohp = nohp;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getrole() {
        return role;
    }

    public void setrole(String role) {
        this.role = role;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}