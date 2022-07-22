package com.example.kmc.SOLogin;

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
import com.example.kmc.SOAdapters.myadapter3;
import com.example.kmc.SOAdapters.myadapter3SO2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SOAmountDBToBen extends AppCompatActivity {
    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;

    myadapter3SO2 adapter;
    String mandal;
    String sector;
    String preferredUnit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soamount_dbto_ben);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mandal");
            String value2 = extras.getString("sector");
            //String value3 = extras.getString("preferredUnit");
            //The key argument here must match that used in the other activity
            mandal = value;
            sector = value2;
            //preferredUnit = value3;

        }
        adapter=new myadapter3SO2(datalist,mandal,sector);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getMandal().toLowerCase(Locale.ROOT).equals(mandal.toLowerCase(Locale.ROOT))) {
                                if(obj.getSpApproved3().equals("yes") && obj.getPreferredUnit().equalsIgnoreCase(sector)) {
                                    if (obj.getPreferredUnit().toLowerCase(Locale.ROOT).equals(sector.toLowerCase(Locale.ROOT))) {
                                        if(!obj.getSoApproved().equals("yes") &&  !obj.getSoApproved().equals("no"))
                                        {
                                            datalist.add(obj);
                                        }
                                    }
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
    public void search(View view) {
        Intent i = new Intent(this, SoAmountDbtoBenSearch.class);
        i.putExtra("mandal",mandal);
        i.putExtra("sector",sector);
        startActivity(i);
    }
}