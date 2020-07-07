package com.example.hallobalita.ui.pemeriksaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.hallobalita.R;
import com.example.hallobalita.ui.pemeriksaan.DataPeriksa;

public class DetailPeriksa extends AppCompatActivity {

    public static String EXTRA_PLAYER = "extra_player";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.periksa_detail);

        TextView textViewKader = findViewById(R.id.txtKader);
        TextView textViewBalita = findViewById(R.id.txtAnak);
        TextView textViewVit = findViewById(R.id.txtVit);
        TextView textViewImun = findViewById(R.id.txtImun);
        TextView textViewTgl = findViewById(R.id.txtTgl);
        TextView textViewBB = findViewById(R.id.txtBB);
        TextView textViewTB = findViewById(R.id.txtTB);

        DataPeriksa dataPeriksa =  getIntent().getParcelableExtra(EXTRA_PLAYER);

        textViewKader.setText(dataPeriksa.getPkader());
        textViewBalita.setText(dataPeriksa.getPbalita());
        textViewVit.setText(dataPeriksa.getPvit());
        textViewImun.setText(dataPeriksa.getPimun());
        textViewTgl.setText(dataPeriksa.getPtgl());
        textViewBB.setText(dataPeriksa.getPbb()+" Kg");
        textViewTB.setText(dataPeriksa.getPtb()+" cm");

    }
}