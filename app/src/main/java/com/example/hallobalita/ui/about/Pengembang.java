package com.example.hallobalita.ui.about;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hallobalita.MenuUser;
import com.example.hallobalita.R;
import com.example.hallobalita.registrasi.Login;
import com.example.hallobalita.user.EditActivity;
import com.example.hallobalita.user.Profil;

public class Pengembang extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    ConnectivityManager conMgr;
    Intent intent;
    String id,username;
    Boolean session = false;
    public final static String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String session_status = "session_status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembang);

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

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);
        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
    }

    public void onBackPressed() {
        if (session) {
            intent = new Intent(Pengembang.this, MenuUser.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }
    }
}