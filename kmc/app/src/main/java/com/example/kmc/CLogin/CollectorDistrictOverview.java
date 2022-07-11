package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.kmc.COverview.myadapterConstituencyOverView;
import com.example.kmc.COverview.myadapterDistrictOverView;
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

public class CollectorDistrictOverview extends AppCompatActivity {


    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<MandalElements> datalist;
    FirebaseFirestore db;
    String district;
    ProgressBar progressBar;
    int totalRegistered;
    int totalSelected;
    int totalApprovedAmount;
    int dbAccountAmount;
    int grounding;

    myadapterDistrictOverView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_district_overview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district= extras.getString("district");
        }
        adapter=new myadapterDistrictOverView(datalist);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

                            db.collection("individuals").whereEqualTo("district",district).get()
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
                                            MandalElements ob=new MandalElements(district,String.valueOf(totalRegistered),String.valueOf(totalSelected),String.valueOf(totalApprovedAmount),String.valueOf(dbAccountAmount),String.valueOf(grounding));
                                            datalist.add(ob);
                                            adapter.notifyDataSetChanged();
                                            totalRegistered=0;
                                            totalSelected=0;
                                            totalApprovedAmount=0;
                                            dbAccountAmount=0;
                                            grounding=0;

                                        }
                                    });
        progressBar.setVisibility(View.GONE);

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

    }
}