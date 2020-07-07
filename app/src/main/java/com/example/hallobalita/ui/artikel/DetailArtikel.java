package com.example.hallobalita.ui.artikel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hallobalita.R;
import com.example.hallobalita.connect.Server;

public class DetailArtikel extends AppCompatActivity {

    public static String EXTRA_ARTIKEL = "extra_artikel";
    public static final String url = Server.URLfoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        ImageView imgGambar = (ImageView) findViewById(R.id.gambar);
        TextView textJudul = (TextView) findViewById(R.id.detailJudul);
        TextView textIsi = (TextView) findViewById(R.id.detailIsi);

        DataArtikel dataArtikel = getIntent().getParcelableExtra(EXTRA_ARTIKEL);

        Glide.with(getApplicationContext())
                .load(url+dataArtikel.getFoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imgGambar);

        textJudul.setText(dataArtikel.getJudul());
        textIsi.setText(dataArtikel.getIsi());
    }
}