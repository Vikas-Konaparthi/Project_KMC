package com.example.kmc.SOLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kmc.PSLogin.PSAddEdit;
import com.example.kmc.PSLogin.PSAmountDBToBen;
import com.example.kmc.PSLogin.PSAmountToDB;
import com.example.kmc.R;
import com.example.kmc.login.Collector_Login;

public class SO_Action extends AppCompatActivity implements View.OnClickListener{

    public CardView card1,card2,card3,card4;
    String mandal;
    String sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_action);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mandal");
            String value2 = extras.getString("sector");
            //String value3 = extras.getString("preferredUnit");
            //The key argument here must match that used in the other activity
            mandal = value;
            sector = value2;
            //preferredUnit = value3;

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
                i = new Intent(this, SOListOfBen.class);
                i.putExtra("mandal",mandal);
                i.putExtra("sector",sector);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, SOAmountDBToBen.class);
                i.putExtra("mandal",mandal);
                i.putExtra("sector",sector);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, SOGrounding.class);
                i.putExtra("mandal",mandal);
                i.putExtra("sector",sector);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, SOMasterReport.class);
                i.putExtra("mandal",mandal);
                i.putExtra("sector",sector);
                startActivity(i);
                break;
        }


    }
}