package com.example.hallobalita.ui.home;

public class DataJadwal {
        private  String tgl, jam;

        public DataJadwal(){
        }

        public DataJadwal(String tgl, String jam){
            this.tgl = tgl;
            this.jam = jam;
        }

        public String getTgl(){return tgl;}

        public void setTgl(String tgl){ this.tgl = tgl; }

        public String getJam(){ return  jam; }

        public void setJam(String jam){ this.jam = jam; }
}
