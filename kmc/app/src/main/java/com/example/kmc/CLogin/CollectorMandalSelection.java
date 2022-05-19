package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.District;
import com.example.kmc.R;
import com.example.kmc.myadapterMandals;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CollectorMandalSelection extends AppCompatActivity {
    public Toolbar toolbar;
    String district;
    FirebaseFirestore db;
    ArrayList<District> datalist;
    RecyclerView recyclerView;
    myadapterMandals adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_mandal_selection);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district = extras.getString("district");
        }
        datalist=new ArrayList<>();
        adapter=new myadapterMandals(datalist, district);
        recyclerView.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        db=FirebaseFirestore.getInstance();
        db.collection(district).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {

                            District obj = d.toObject(District.class);

                            obj.setUid(d.getId().toString());
                            datalist.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                });

        Toast.makeText(this, district, Toast.LENGTH_SHORT).show();
    }
}