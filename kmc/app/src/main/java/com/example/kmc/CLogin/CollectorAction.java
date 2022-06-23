package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kmc.PSLogin.PSAddEdit;
import com.example.kmc.PSLogin.PSAmountToDB;
import com.example.kmc.R;
import com.example.kmc.SPLogin.ListOfBen;
import com.example.kmc.login.Collector_Login;
import com.example.kmc.login.SOLogin;

public class CollectorAction extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4,card5;
    String village;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_action);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        card5 = (CardView) findViewById(R.id.c5);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            village = extras.getString("village");
        }else{
            Log.d("extra", "no");
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
                i = new Intent(this, CollectorListOfBen.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, CollectorAmountToDB.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, CollectorAmountDBToBen.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, CollectorGrounding.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c5:
                i = new Intent(this, CollectorMasterReport.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
        }


    }
}