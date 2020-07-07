package com.example.hallobalita.ui.pemeriksaan;

import android.os.Parcel;
import android.os.Parcelable;

public class DataPeriksa implements Parcelable {
    String pkader,pbalita,pvit,pimun,ptgl,pbb,ptb;

    public DataPeriksa(String pkader, String pbalita, String pvit, String pimun, String ptgl, String pbb, String ptb){
        this.pkader = pkader;
        this.pbalita = pbalita;
        this.pvit = pvit;
        this.pimun = pimun;
        this.ptgl = ptgl;
        this.pbb = pbb;
        this.ptb = ptb;
    }

    protected DataPeriksa(Parcel in) {
        this.pkader = in.readString();
        this.pbalita = in.readString();
        this.pvit = in.readString();
        this.pimun = in.readString();
        this.ptgl = in.readString();
        this.pbb = in.readString();
        this.ptb = in.readString();
    }

    public static final Parcelable.Creator<DataPeriksa> CREATOR = new Parcelable.Creator<DataPeriksa>() {
        @Override
        public DataPeriksa createFromParcel(Parcel source) {
            return new DataPeriksa(source);
        }

        @Override
        public DataPeriksa[] newArray(int size) {
            return new DataPeriksa[size];
        }
    };

    public String getPkader() { return pkader; }
    public String getPbalita() { return pbalita; }
    public String getPvit() { return pvit; }
    public String getPimun() { return pimun; }
    public String getPtgl() { return ptgl; }
    public String getPbb() { return pbb; }
    public String getPtb() { return ptb; }

    public void setPkader(String pkader) { this.pkader = pkader; }

    public void setPbalita(String pbalita) { this.pbalita = pbalita; }

    public void setPvit(String pvit) { this.pvit = pvit; }

    public void setPimun(String pimun) { this.pimun = pimun; }

    public void setPtgl(String ptgl) { this.ptgl = ptgl; }

    public void setPbb(String pbb) { this.pbb = pbb; }

    public void setPtb(String ptb) { this.ptb = ptb; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pkader);
        parcel.writeString(pbalita);
        parcel.writeString(pvit);
        parcel.writeString(pimun);
        parcel.writeString(ptgl);
        parcel.writeString(pbb);
        parcel.writeString(ptb);
    }
}
