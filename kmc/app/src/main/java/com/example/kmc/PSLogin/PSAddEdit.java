package com.example.kmc.PSLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.PSAdapters.myadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PSAddEdit extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;

    myadapter adapter;
    String village;
    String district;
    String mandal;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pszone);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new myadapter(datalist);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village");
            village=value;
            mandal=extras.getString("mandal");
            district=extras.getString("district");
            //The key argument here must match that used in the other activity

        }else{
            Log.d("extra", "no");
        }


        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d:list)
                            {

                                Individual obj=d.toObject(Individual.class);
                                if(obj.getVillage()!=null)
                                {
                                    if(obj.getVillage().toLowerCase(Locale.ROOT).toString().equals(village.toLowerCase(Locale.ROOT)))
                                    {
                                        datalist.add(obj);
                                    }
                                }


                            }
                            adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);


    }

    public void addIndividual(View view) {
//        Intent i = new Intent(PSZone.this, addIndividual.class);
//        startActivity(i);

        Intent intent = new Intent(getApplicationContext(), addIndividual.class);
        intent.putExtra("village",village);
        intent.putExtra("mandal",mandal);
        intent.putExtra("district",district);
        startActivity(intent);
        finish();
    }
}