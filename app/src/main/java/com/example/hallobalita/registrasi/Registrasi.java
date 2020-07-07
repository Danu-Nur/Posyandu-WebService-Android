package com.example.hallobalita.registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hallobalita.connect.AppController;
import com.example.hallobalita.R;
import com.example.hallobalita.connect.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Registrasi extends AppCompatActivity {

    public static final String urljk = Server.URL + "kelamin.php";

    public static final String url = Server.URL + "registrasi.php";

    ProgressDialog pDialog;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    ConnectivityManager conMgr;
    int success;
    Integer pilihID;

    Spinner jenis_spinner;
    ArrayList<DataJenis> dataJenisList = new ArrayList<>();
    List<String> valueJK = new ArrayList<>();
    List<String> valueID = new ArrayList<>();
    TextView texIsi;

    Button btn_register, btn_login;
    EditText txt_noKK, txt_namaAyah, txt_namaIbu, txt_namaBalita, tglBalita,
             txt_Alamat, txt_noTelp,txt_username, txt_password, txt_confirm;

    Intent intent;
    private static final String TAG = Registrasi.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

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

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_noKK = (EditText) findViewById(R.id.txt_noKK);
        txt_namaAyah = (EditText) findViewById(R.id.txt_namaAyah);
        txt_namaIbu = (EditText) findViewById(R.id.txt_namaIbu);
        txt_namaBalita = (EditText) findViewById(R.id.txt_namaBalita);
        tglBalita = (EditText) findViewById(R.id.txt_tglBalita);
        txt_Alamat = (EditText) findViewById(R.id.txt_alamat);
        txt_noTelp = (EditText) findViewById(R.id.txt_noTelp);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirm = (EditText) findViewById(R.id.txt_confirm_password);

        //untuk edit tanggal
        myCalendar = Calendar.getInstance();
        loadCalender();
        tglBalita.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Registrasi.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//end edit tgl
        //spinner
        jenis_spinner = (Spinner)  findViewById(R.id.spinner_jK);

        loadDataSpinner();
        jenis_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
//end spinner

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(Registrasi.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                    String kk = txt_noKK.getText().toString();
                    String ayah = txt_namaAyah.getText().toString();
                    String ibu = txt_namaIbu.getText().toString();
                    String balita = txt_namaBalita.getText().toString();
                    String ttl = tglBalita.getText().toString();
                    String jk = pilihID.toString();
                    String alamat = txt_Alamat.getText().toString();
                    String telp = txt_noTelp.getText().toString();
                    String username = txt_username.getText().toString();
                    String password = txt_password.getText().toString();
                    String confirm_password = txt_confirm.getText().toString();

                    if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkRegister(kk,ayah,ibu,balita,ttl,jk,alamat,telp,username,password,confirm_password);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //mengirim data ke database
    private void checkRegister(final String kk, final String ayah, final String ibu, final String balita, final String ttl, final String jk,
                               final String alamat, final String telp, final String username, final String password, final String confirm_password) {
        pDialog = new ProgressDialog(Registrasi.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url,
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

                        txt_noKK.setText("");
                        txt_namaAyah.setText("");
                        txt_namaIbu.setText("");
                        txt_namaBalita.setText("");
                        tglBalita.setText("");
                        txt_Alamat.setText("");
                        txt_noTelp.setText("");
                        txt_username.setText("");
                        txt_password.setText("");
                        txt_confirm.setText("");

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
                params.put("confirm_password", confirm_password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    public void loadCalender(){
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tglBalita.setText(sdf.format(myCalendar.getTime()));
            }
        };
    }
//load spinner
    public void loadDataSpinner(){
        dataJenisList.clear();
        pDialog = new ProgressDialog(Registrasi.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urljk,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray JKArray = obj.getJSONArray("result");

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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Registrasi.this,
                                    android.R.layout.simple_spinner_item, valueJK);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            jenis_spinner.setAdapter(adapter);
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
                        Toast.makeText(Registrasi.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog(); }
                });

        requestQueue.add(stringRequest);

    }
    //end spinner

    public void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(Registrasi.this, Login.class);
        finish();
        startActivity(intent);
    }

}