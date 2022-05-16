package com.example.kmc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kmc.login.PSLogin;
import com.example.kmc.login.SOLogin;
import com.example.kmc.login.SPLogin;

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
//                i = new Intent(this,notifications.class);
//                startActivity(i);
                break;
        }


    }
}