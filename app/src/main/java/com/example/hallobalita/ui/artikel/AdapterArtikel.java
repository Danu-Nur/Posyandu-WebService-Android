package com.example.hallobalita.ui.artikel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hallobalita.R;
import com.example.hallobalita.connect.Server;

import java.util.List;

public class AdapterArtikel extends ArrayAdapter<DataArtikel> {
    private List<DataArtikel> dataArtikelList;
    private Context context;
    public static final String url = Server.URLfoto;


    public  AdapterArtikel(List<DataArtikel> dataArtikelList, Context context){
        super(context, R.layout.artikel_fragment, dataArtikelList);
        this.dataArtikelList = dataArtikelList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);

        View listViewItem = inflater.inflate(R.layout.item_artikel, null, false);

        ImageView imgartikel =(ImageView) listViewItem.findViewById(R.id.imgArtikel);
        TextView txtJudul =(TextView) listViewItem.findViewById(R.id.judulArtikel);
        TextView txtIsi =(TextView) listViewItem.findViewById(R.id.isiArtikel);

        final DataArtikel dataArtikel = dataArtikelList.get(position);

        Glide.with(getContext())
                .load(url+dataArtikel.getFoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imgartikel);

        txtJudul.setText(dataArtikel.getJudul());
        txtIsi.setText(dataArtikel.getIsi());

        return listViewItem;
    }
}
