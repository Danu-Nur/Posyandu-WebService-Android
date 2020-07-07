package com.example.hallobalita.ui.artikel;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hallobalita.ui.artikel.DataArtikel;

public class DataArtikel implements Parcelable {
    String judul,foto,isi;

    public DataArtikel(String judul, String foto, String isi){
        this.judul = judul;
        this.foto = foto;
        this.isi = isi;
    }

    protected DataArtikel(Parcel in){
        this.judul = in.readString();
        this.foto = in.readString();
        this.isi = in.readString();
    }

    public static final Parcelable.Creator<DataArtikel> CREATOR = new Parcelable.Creator<DataArtikel>() {
        @Override
        public DataArtikel createFromParcel(Parcel source) {
            return new DataArtikel(source);
        }

        @Override
        public DataArtikel[] newArray(int size) {
            return new DataArtikel[size];
        }
    };

    public String getJudul() { return judul; }

    public String getFoto() { return foto; }

    public String getIsi() { return isi; }

    public void setJudul(String judul) { this.judul = judul; }

    public void setFoto(String foto) { this.foto = foto; }

    public void setIsi(String isi) { this.isi = isi; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(judul);
        parcel.writeString(foto);
        parcel.writeString(isi);
    }
}
