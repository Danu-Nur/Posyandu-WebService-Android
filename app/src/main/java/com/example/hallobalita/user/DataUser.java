package com.example.hallobalita.user;

public class DataUser {
    private String iduser,kk,ayah,ibu,balita,ttl,jkelamin,alamat,telp,username,pasword;

    public DataUser(){
    }


    public DataUser(String iduser,String kk, String ayah, String ibu, String balita, String ttl, String jkelamin, String alamat, String telp, String username, String pasword){
        this.iduser = iduser;
        this.kk = kk;
        this.ayah = ayah;
        this.ibu = ibu;
        this.balita = balita;
        this.ttl = ttl;
        this.jkelamin = jkelamin;
        this.alamat = alamat;
        this.telp = telp;
        this.username = username;
        this.pasword = pasword;
    }

    public String getIduser() { return iduser; }

    public String getKk() { return kk; }

    public String getAyah() { return ayah; }

    public String getIbu() { return ibu; }

    public String getBalita() { return balita; }

    public String getTtl() { return ttl; }

    public String getJkelamin() { return jkelamin; }

    public String getAlamat() { return alamat; }

    public String getTelp() { return telp; }

    public String getUsername() { return username; }

    public String getPasword() { return pasword; }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public void setKk(String kk) {
        this.kk = kk;
    }

    public void setAyah(String ayah) {
        this.ayah = ayah;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public void setBalita(String balita) {
        this.balita = balita;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public void setJkelamin(String jkelamin) {
        this.jkelamin = jkelamin;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }
}
