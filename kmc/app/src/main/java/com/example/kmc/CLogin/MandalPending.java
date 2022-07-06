package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.kmc.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MandalPending extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<String> datalist;
    FirebaseFirestore db;
    String district;
    ProgressBar progressBar;
    Button mandalPend;
    int psPending;
    int spPending;
    int ctrPending;
    int groundingPending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandal_pending);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district = extras.getString("district");
        }
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mandalPend=(Button) findViewById(R.id.mandalPending);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

    }
}