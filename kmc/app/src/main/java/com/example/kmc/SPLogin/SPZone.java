package com.example.kmc.SPLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.myadapter2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SPZone extends AppCompatActivity {
    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;

    myadapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spzone);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new myadapter2(datalist);
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
            Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        }
    }
}