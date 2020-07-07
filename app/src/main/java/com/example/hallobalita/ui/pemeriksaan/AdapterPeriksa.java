package com.example.hallobalita.ui.pemeriksaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hallobalita.R;

import java.util.List;

public class AdapterPeriksa extends ArrayAdapter<DataPeriksa> {
    private List<DataPeriksa> dataPeriksaList;

    private Context context;

    public  AdapterPeriksa(List<DataPeriksa> dataPeriksaList, Context context){
        super(context, R.layout.list_periksa, dataPeriksaList);
        this.dataPeriksaList = dataPeriksaList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);

        View listViewItem = inflater.inflate(R.layout.list_periksa, null, false);

        TextView ViewKader = listViewItem.findViewById(R.id.kaderItem);
        TextView ViewtglPeriksa = listViewItem.findViewById(R.id.tglItem);

        final DataPeriksa dataPeriksa = dataPeriksaList.get(position);

        ViewKader.setText(dataPeriksa.getPkader());
        ViewtglPeriksa.setText(dataPeriksa.getPtgl());

        return listViewItem;
    }
}
