package com.example.hallobalita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hallobalita.registrasi.Login;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    String id, username;
    Boolean session = false;
    public static final String session_status = "session_status";
    ConnectivityManager conMgr;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //cek session login
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(2000);

                }catch (InterruptedException e){
                    e.printStackTrace();

                }finally {

                    if (session) {
                        Intent intent = new Intent(MainActivity.this, MenuUser.class);
                        intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_USERNAME, username);
                        finish();
                        startActivity(intent);

                    }else{
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                    }

                    finish();
                }
            }
        };
        thread.start();
    }
}