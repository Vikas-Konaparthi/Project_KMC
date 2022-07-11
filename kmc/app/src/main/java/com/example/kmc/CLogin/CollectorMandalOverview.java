package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.kmc.COverview.myadapterMandalOverView;
import com.example.kmc.CollectorAdapters.myadapter4Collector4;
import com.example.kmc.District;
import com.example.kmc.Individual;
import com.example.kmc.MandalElements;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CollectorMandalOverview extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<MandalElements> datalist;
    FirebaseFirestore db;
    String district;
    String constituency;
    ProgressBar progressBar;
    int totalRegistered;
    int totalSelected;
    int totalApprovedAmount;
    int dbAccountAmount;
    int grounding;

    myadapterMandalOverView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_mandal_overview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district= extras.getString("district");
            constituency= extras.getString("constituency");
        }
        adapter=new myadapterMandalOverView(datalist,district);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        db.collection(district).whereEqualTo("constituency",constituency).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {
                            District obj = d.toObject(District.class);
                            obj.setUid(d.getId().toString());
                            db.collection("individuals").whereEqualTo("mandal",obj.getUid()).get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();

                                            for(DocumentSnapshot d:list)
                                            {
                                                Individual obj2=d.toObject(Individual.class);
                                                totalRegistered=totalRegistered+1;
                                                if(obj2.getSpApproved().equals("yes"))
                                                {
                                                    totalSelected=totalSelected+1;
                                                }
                                                totalApprovedAmount=totalApprovedAmount+Integer.parseInt(obj2.getApprovalAmount());
                                                dbAccountAmount=dbAccountAmount+Integer.parseInt(obj2.getDbAccount());
                                                if(obj2.getGroundingStatus().equals("yes")||Integer.parseInt(obj2.getApprovalAmount())>=990000)
                                                {
                                                    grounding=grounding+1;
                                                }
                                            }
                                            MandalElements ob=new MandalElements(obj.getUid(),String.valueOf(totalRegistered),String.valueOf(totalSelected),String.valueOf(totalApprovedAmount),String.valueOf(dbAccountAmount),String.valueOf(grounding));
                                            datalist.add(ob);
                                            adapter.notifyDataSetChanged();
                                            totalRegistered=0;
                                            totalSelected=0;
                                            totalApprovedAmount=0;
                                            dbAccountAmount=0;
                                            grounding=0;

                                        }
                                    });
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                });

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

    }
}