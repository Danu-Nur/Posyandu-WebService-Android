package com.example.hallobalita.ui.kontak;

public class DataKontak {
    private  String nama, telp;

    public DataKontak(){
    }

    public DataKontak(String nama, String telp){
        this.nama = nama;
        this.telp = telp;
    }

    public String getNama(){return nama;}

    public void setNama(String nama){ this.nama = nama; }

    public String getTelp(){ return  telp; }

    public void setTelp(String telp){ this.telp = telp; }
}
