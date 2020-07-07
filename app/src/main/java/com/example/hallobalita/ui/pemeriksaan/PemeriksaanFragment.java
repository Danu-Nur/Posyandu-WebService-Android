package com.example.hallobalita.ui.pemeriksaan;

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

import com.example.hallobalita.connect.Server;
import com.example.hallobalita.registrasi.Login;
import com.example.hallobalita.R;
import com.example.hallobalita.registrasi.Registrasi;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PemeriksaanFragment extends Fragment {

    public static PemeriksaanFragment newInstance() {
        return new PemeriksaanFragment();
    }

    private List<DataPeriksa> dataPeriksaList;
    ProgressDialog pDialog;
    private static final String TAG = Registrasi.class.getSimpleName();
    public final static String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String url = Server.URL + "periksa.php?id=";
    public static final String session_status = "session_status";
    SharedPreferences sharedpreferences;
    ConnectivityManager conMgr;
    String id,username;
    Boolean session = false;

    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.periksa_fragment, container, false);
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
//        load data periksa
        listView = root.findViewById(R.id.listPeriksa);
        dataPeriksaList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DataPeriksa dataPeriksa = dataPeriksaList.get(position);
                Intent i = new Intent(getContext(), DetailPeriksa.class);
                i.putExtra(DetailPeriksa.EXTRA_PLAYER, dataPeriksa);
                getActivity().startActivity(i);

            }
        });

        loadDataPeriksa();

        return  root;
    }


    private void loadDataPeriksa(){

        dataPeriksaList.clear();
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray JKArray = obj.getJSONArray("result");

                            for (int i = 0; i < JKArray.length(); i++) {
                                JSONObject JKObject = JKArray.getJSONObject(i);
//
                                DataPeriksa dataPeriksa = new DataPeriksa(
                                        JKObject.getString("kader"),
                                        JKObject.getString("balita"),
                                        JKObject.getString("vit"),
                                        JKObject.getString("imun"),
                                        JKObject.getString("tgl"),
                                        JKObject.getString("bb"),
                                        JKObject.getString("tb")
                                        );
                                dataPeriksaList.add(dataPeriksa);
                            }
                            AdapterPeriksa adapter = new AdapterPeriksa(dataPeriksaList,getContext());
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