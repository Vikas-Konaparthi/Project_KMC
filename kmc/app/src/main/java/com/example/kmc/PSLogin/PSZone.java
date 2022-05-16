package com.example.kmc.PSLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.myadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PSZone extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;

    myadapter adapter;


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
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d:list)
                            {
                                Individual obj=d.toObject(Individual.class);
                                datalist.add(obj);
                            }
                            adapter.notifyDataSetChanged();
                    }
                });

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("zone");
            //The key argument here must match that used in the other activity
            Toast.makeText(this, "hello "+value, Toast.LENGTH_SHORT).show();
        }


    }

    public void addIndividual(View view) {
//        Intent i = new Intent(PSZone.this, addIndividual.class);
//        startActivity(i);

        Intent intent = new Intent(getApplicationContext(), addIndividual.class);
        startActivity(intent);
        finish();
    }
}