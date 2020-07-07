package com.example.hallobalita.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hallobalita.MenuUser;
import com.example.hallobalita.R;
import com.example.hallobalita.connect.Server;
import com.example.hallobalita.registrasi.DataJenis;
import com.example.hallobalita.registrasi.Login;
import com.example.hallobalita.registrasi.Registrasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.hallobalita.registrasi.Login.TAG_ID;

public class Profil extends AppCompatActivity {

    TextView txtKK,txtAYAH,txtIBU,txtBALITA,txtTTL,txtJKELAMIN,txtALAMAT,txtTELP,txtUSERN,txtPASS;
    ArrayList<DataUser> dataUserList = new ArrayList<>();
    ProgressDialog pDialog;
    private static final String TAG = Registrasi.class.getSimpleName();
    public final static String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String jurl = Server.URL + "tampil_user_id.php?id=";
    public static final String session_status = "session_status";
    SharedPreferences sharedpreferences;
    ConnectivityManager conMgr;
    Intent intent;
    String id,username;
    Boolean session = false;
    Button keluar,editbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        txtKK =(TextView) findViewById(R.id.txt_noKK1);
        txtAYAH =(TextView) findViewById(R.id.txt_namaAyah1);
        txtIBU =(TextView) findViewById(R.id.txt_namaIbu1);
        txtBALITA =(TextView) findViewById(R.id.txt_namaBalita1);
        txtTTL =(TextView) findViewById(R.id.txt_tglBalita1);
        txtJKELAMIN =(TextView) findViewById(R.id.txt_jenis1);
        txtALAMAT =(TextView) findViewById(R.id.txt_Alamat1);
        txtTELP =(TextView) findViewById(R.id.txt_Telp1);
        txtUSERN =(TextView) findViewById(R.id.txt_Username1);
        txtPASS =(TextView) findViewById(R.id.txt_Password1);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);
        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

        loadDataUser();
        editbtn = (Button)findViewById(R.id.btn_Edit);
        editbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (session) {
                    Intent intent = new Intent(Profil.this, EditActivity.class);
                    intent.putExtra(TAG_ID, id);
                    intent.putExtra(TAG_USERNAME, username);
                    finish();
                    startActivity(intent);
                }
            }
        });

        keluar = (Button)findViewById(R.id.btnKeluar);
        keluar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), Login.class);
                finish();
                startActivity(intent);
            }
        });

    }
    public void loadDataUser(){
        dataUserList.clear();
        pDialog = new ProgressDialog(Profil.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, jurl+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray JKArray = obj.getJSONArray("result");

                            for (int i = 0; i < JKArray.length(); i++) {
                                JSONObject JKObject = JKArray.getJSONObject(i);
//
                                DataUser dataUser = new DataUser();
                                dataUser.setIduser(JKObject.getString("iduser"));
                                dataUser.setKk(JKObject.getString("kkuser"));
                                dataUser.setAyah(JKObject.getString("ayahuser"));
                                dataUser.setIbu(JKObject.getString("ibuuser"));
                                dataUser.setBalita(JKObject.getString("balitauser"));
                                dataUser.setTtl(JKObject.getString("ttluser"));
                                dataUser.setJkelamin(JKObject.getString("jkelaminuser"));
                                dataUser.setAlamat(JKObject.getString("alamatuser"));
                                dataUser.setTelp(JKObject.getString("telpuser"));
                                dataUser.setUsername(JKObject.getString("usernameuser"));
                                dataUser.setPasword(JKObject.getString("pasworduser"));
                                dataUserList.add(dataUser);
                            }
                            for (int i = 0; i < dataUserList.size(); i++) {
                                txtKK.setText(dataUserList.get(i).getKk());
                                txtAYAH.setText(dataUserList.get(i).getAyah());
                                txtIBU.setText(dataUserList.get(i).getIbu());
                                txtBALITA.setText(dataUserList.get(i).getBalita());
                                txtTTL.setText(dataUserList.get(i).getTtl());
                                txtJKELAMIN.setText(dataUserList.get(i).getJkelamin());
                                txtALAMAT.setText(dataUserList.get(i).getAlamat());
                                txtTELP.setText(dataUserList.get(i).getTelp());
                                txtUSERN.setText(dataUserList.get(i).getUsername());
                                txtPASS.setText(dataUserList.get(i).getPasword());
                            }
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
                        Toast.makeText(Profil.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        if (session) {
            intent = new Intent(Profil.this, MenuUser.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }
    }
}