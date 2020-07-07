package com.example.hallobalita.registrasi;

public class DataJenis {
    private  String idjk, jk;

    public DataJenis(){
    }

    public DataJenis(String idjk, String jk){
        this.idjk = idjk;
        this.jk = jk;
    }

    public String getIdjk(){return idjk;}

    public void setIdjk(String idjk){ this.idjk = idjk; }

    public String getJk(){ return  jk; }

    public void setJk(String jk){ this.jk = jk; }
}
