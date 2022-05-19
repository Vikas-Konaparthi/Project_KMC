package com.example.kmc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.kmc.login.Collector_Login;
import com.example.kmc.login.PSLogin;
import com.example.kmc.login.SOLogin;
import com.example.kmc.login.SPLogin;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        try {
            if(!isNetworkConnected())
            {
               final Toast toast= Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private boolean isNetworkConnected(){
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.c1:
                i = new Intent(this, PSLogin.class);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, SPLogin.class);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, SOLogin.class);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, Collector_Login.class);
                startActivity(i);
                break;
        }


    }
}