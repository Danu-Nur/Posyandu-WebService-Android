package com.example.hallobalita.ui.artikel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hallobalita.R;
import com.example.hallobalita.connect.Server;
import com.example.hallobalita.registrasi.Login;
import com.example.hallobalita.registrasi.Registrasi;
import com.example.hallobalita.ui.pemeriksaan.AdapterPeriksa;
import com.example.hallobalita.ui.artikel.DataArtikel;
import com.example.hallobalita.ui.pemeriksaan.DetailPeriksa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtikelFragment extends Fragment {

    public static ArtikelFragment newInstance() {
        return new ArtikelFragment();
    }

    private List<DataArtikel> dataArtikelList;
    ProgressDialog pDialog;
    private static final String TAG = Registrasi.class.getSimpleName();
    public final static String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String url = Server.URL + "artikel.php";
    public static final String session_status = "session_status";
    SharedPreferences sharedpreferences;
    ConnectivityManager conMgr;
    String id,username;
    Boolean session = false;

    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.artikel_fragment, container, false);
        //        cek koneksi internet
        conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
        //        session dan id
        sharedpreferences = getActivity().getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);
        id = getActivity().getIntent().getStringExtra(TAG_ID);
        username = getActivity().getIntent().getStringExtra(TAG_USERNAME);
//        load data artikel
        listView = root.findViewById(R.id.listArtikel);
        dataArtikelList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DataArtikel dataArtikel = dataArtikelList.get(position);
                Intent i = new Intent(getContext(), DetailArtikel.class);
                i.putExtra(DetailArtikel.EXTRA_ARTIKEL, dataArtikel);
                getActivity().startActivity(i);
//                Toast.makeText(getContext(), "data foto : "+dataArtikel.getFoto()+"\n data judul : "+dataArtikel.getJudul()+"data isi : "+dataArtikel.getIsi(),
//                        Toast.LENGTH_LONG).show();

            }
        });

        loadDataArtikel();

        return  root;
    }

    private void loadDataArtikel(){

        dataArtikelList.clear();
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray JKArray = obj.getJSONArray("result");

                            for (int i = 0; i < JKArray.length(); i++) {
                                JSONObject JKObject = JKArray.getJSONObject(i);
//
                                DataArtikel dataArtikel = new DataArtikel(
                                        JKObject.getString("judul"),
                                        JKObject.getString("foto"),
                                        JKObject.getString("isi")
                                );
                                dataArtikelList.add(dataArtikel);
                            }
                            AdapterArtikel adapter = new AdapterArtikel(dataArtikelList,getContext());
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog(); }
                });

        requestQueue.add(stringRequest);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}