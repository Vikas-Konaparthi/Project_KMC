package com.example.kmc.SPLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kmc.PSLogin.PSMasterReport;
import com.example.kmc.R;
import com.example.kmc.login.Collector_Login;
import com.example.kmc.login.SOLogin;
import com.example.kmc.login.SPLogin;

public class SP_Action extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4,card5;
    String village1;
    String village2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_action);

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
            String value = extras.getString("village1");
            String value2 = extras.getString("village2");
            //The key argument here must match that used in the other activity
            village1 = value;
            village2 = value2;
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
                i = new Intent(this, ListOfBen.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, SPAmountToDB.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, SPAmountDBToBen.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, SPGrounding.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
            case R.id.c5:
                i = new Intent(this, SPMasterReport.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
        }


    }
}