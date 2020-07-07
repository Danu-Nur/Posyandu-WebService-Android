package com.example.hallobalita.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.hallobalita.connect.AppController;
import com.example.hallobalita.connect.Server;
import com.example.hallobalita.registrasi.DataJenis;
import com.example.hallobalita.registrasi.Login;
import com.example.hallobalita.registrasi.Registrasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    EditText editKK,editAYAH,editIBU,editBALITA,editTTL,editALAMAT,editTELP,editUSERN,editPASS;
    Spinner jk_spinner;
    private static final String TAG = Registrasi.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    ArrayList<DataUser> dataUserList = new ArrayList<>();
    ArrayList<DataJenis> dataJenisList = new ArrayList<>();
    List<String> valueJK = new ArrayList<>();
    List<String> valueID = new ArrayList<>();
    ProgressDialog pDialog;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    public static final String urljk = Server.URL + "kelamin.php";
    public static final String jurl = Server.URL + "tampil_user_id.php?id=";
    public static final String editurl = Server.URL + "edit_user.php?id=";
    public final static String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String session_status = "session_status";
    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    ConnectivityManager conMgr;
    Intent intent;
    String id,username;
    Boolean session = false;
    int success;
    Integer pilihID,tanggal;
    Button simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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

        editKK =(EditText) findViewById(R.id.edit_noKK);
        editAYAH =(EditText) findViewById(R.id.edit_namaAyah);
        editIBU =(EditText) findViewById(R.id.edit_namaIbu);
        editBALITA =(EditText) findViewById(R.id.edit_namaBalita);
        editTTL =(EditText) findViewById(R.id.edit_tglBalita);
        editALAMAT =(EditText) findViewById(R.id.edit_alamat);
        editTELP =(EditText) findViewById(R.id.edit_noTelp);
        editUSERN =(EditText) findViewById(R.id.edit_username);
        editPASS =(EditText) findViewById(R.id.edit_password);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);
        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTTL.setText(sdf.format(myCalendar.getTime()));
            }
        };
        editTTL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        jk_spinner = (Spinner)findViewById(R.id.editspinner_jK);
        loadDataSpinner();
        jk_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String pilih = dataJenisList.get(position).getIdjk();
                pilihID = Integer.valueOf(pilih);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        loadDataUser();

        simpan = (Button) findViewById(R.id.btn_simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String kk = editKK.getText().toString();
                String ayah = editAYAH.getText().toString();
                String ibu = editIBU.getText().toString();
                String balita = editBALITA.getText().toString();
                String ttl = editTTL.getText().toString();
                String jk = pilihID.toString();
                String alamat = editALAMAT.getText().toString();
                String telp = editTELP.getText().toString();
                String username = editUSERN.getText().toString();
                String password = editPASS.getText().toString();

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    updateProfil(kk,ayah,ibu,balita,ttl,jk,alamat,telp,username,password);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadDataUser(){
        dataUserList.clear();
        pDialog = new ProgressDialog(EditActivity.this);
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
                                editKK.setText(dataUserList.get(i).getKk());
                                editAYAH.setText(dataUserList.get(i).getAyah());
                                editIBU.setText(dataUserList.get(i).getIbu());
                                editBALITA.setText(dataUserList.get(i).getBalita());
                                editTTL.setText(dataUserList.get(i).getTtl());
                                editALAMAT.setText(dataUserList.get(i).getAlamat());
                                editTELP.setText(dataUserList.get(i).getTelp());
                                editUSERN.setText(dataUserList.get(i).getUsername());
                                editPASS.setText(dataUserList.get(i).getPasword());
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
                        Toast.makeText(EditActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog(); }
                });

        requestQueue.add(stringRequest);

    }

    //load spinner
    private void loadDataSpinner(){
//        pDialog = new ProgressDialog(EditActivity.this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading...");
//        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urljk,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray JKArray = obj.getJSONArray("result");
                            dataJenisList.clear();

                            for (int i = 0; i < JKArray.length(); i++) {
                                JSONObject JKObject = JKArray.getJSONObject(i);
//
                                DataJenis dataJenis = new DataJenis();
                                dataJenis.setIdjk(JKObject.getString("ID_JK"));
                                dataJenis.setJk(JKObject.getString("JENIS_KELAMIN"));
                                dataJenisList.add(dataJenis);
                            }
                            valueID = new ArrayList<String>();
                            valueJK = new ArrayList<String>();
                            for (int i = 0; i < dataJenisList.size(); i++) {
                                valueID.add(dataJenisList.get(i).getIdjk());
                                valueJK.add(dataJenisList.get(i).getJk());

                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditActivity.this,
                                    android.R.layout.simple_spinner_item, valueJK);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            jk_spinner.setAdapter(adapter);
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
                        Toast.makeText(EditActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog(); }
                });

        requestQueue.add(stringRequest);

    }

    private void updateProfil(final String kk, final String ayah, final String ibu, final String balita, final String ttl, final String jk,
                               final String alamat, final String telp, final String username, final String password) {
        pDialog = new ProgressDialog(EditActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, editurl+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Register Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            // Check for error node in json
                            if (success == 1) {

                                Log.e("Successfully Register!", jObj.toString());

                                Toast.makeText(getApplicationContext(),
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                editKK.setText("");
                                editAYAH.setText("");
                                editIBU.setText("");
                                editBALITA.setText("");
                                editTTL.setText("");
                                editALAMAT.setText("");
                                editTELP.setText("");
                                editUSERN.setText("");
                                editPASS.setText("");

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("kk", kk);
                params.put("ayah", ayah);
                params.put("ibu", ibu);
                params.put("balita", balita);
                params.put("ttl", ttl);
                params.put("jk", jk);
                params.put("alamat", alamat);
                params.put("telp", telp);
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
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
            intent = new Intent(EditActivity.this, Profil.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }
    }
}