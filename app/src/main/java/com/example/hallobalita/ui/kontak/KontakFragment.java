package com.example.hallobalita.ui.kontak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hallobalita.registrasi.Login;
import com.example.hallobalita.R;
import com.example.hallobalita.registrasi.Registrasi;
import com.example.hallobalita.connect.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KontakFragment extends Fragment {
    SharedPreferences sharedpreferences;
    ProgressDialog pDialog;
    ConnectivityManager conMgr;
    private static final String TAG = Registrasi.class.getSimpleName();
    public static final String url = Server.URL + "kontak.php";


    ArrayList<DataKontak> dataKontakList = new ArrayList<>();
    ArrayList<String> valueKontak = new ArrayList<>();
    ListView kontakView;


    public static KontakFragment newInstance() {
        return new KontakFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.kontak_fragment, container, false);

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

        sharedpreferences = getActivity().getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        kontakView = (ListView)root.findViewById(R.id.listKontak);
        loadDataJadwal();
        return  root;
    }

    private void loadDataJadwal(){
        dataKontakList.clear();
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
                                DataKontak dataKontak = new DataKontak();
                                dataKontak.setNama(JKObject.getString("nama"));
                                dataKontak.setTelp(JKObject.getString("telp"));
                                dataKontakList.add(dataKontak);
                            }
                            valueKontak = new ArrayList<String>();
                            for (int i = 0; i < dataKontakList.size(); i++) {
                                valueKontak.add("\n\t\tKADER : "+dataKontakList.get(i).getNama()
                                        +"\n\n\t\tTELP : "+dataKontakList.get(i).getTelp()+"\n");
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, valueKontak);
                            kontakView.setAdapter(adapter);
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
    //end spinner

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}