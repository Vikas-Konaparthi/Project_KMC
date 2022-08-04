package com.example.kmc.CLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.CollectorAdapters.myadapter5;
import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.CollectorAdapters.myadapter4;
import com.example.kmc.SelectionElements;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CollectorListOfBen2 extends AppCompatActivity implements com.example.kmc.List{
    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    ArrayList<SelectionElements> selected;
    String village;
    String district;
    ProgressBar progressBar;
    ImageButton checkAll;
    ImageButton cancelAll;
    myadapter5 adapter;
    ImageButton unitsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_list_ben);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkAll=findViewById(R.id.checkAll);
        unitsearch=findViewById(R.id.unit_search);
        unitsearch.setVisibility(View.VISIBLE);
        cancelAll=findViewById(R.id.cancelAll);
        datalist=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
          //  village= extras.getString("village");
            district= extras.getString("district");
        }
        adapter=new myadapter5(datalist,village,CollectorListOfBen2.this,CollectorListOfBen2.this);
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
                            if(obj.getDistrict().equalsIgnoreCase(district)) {
                                if (obj.getSpApproved().equals("yes") && !obj.getCtrBenApproved().equalsIgnoreCase("yes"))
                                    datalist.add(obj);
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
        Intent i = new Intent(this, CollectorSearch2.class);
        i.putExtra("district",district);
     //   i.putExtra("village",village);
        startActivity(i);
    }

    public void cancelAll(View view) {
        for(SelectionElements s:selected) {

            String approved = "no";
            String status = "Rejected by Collector";
            String spApproved = "NA";
            updateData(s.getAadhar(), approved, status, spApproved);
        }
    }

    public void checkAll(View view) {
        for(SelectionElements s:selected) {
            String approved = "yes";
            String status = "Waiting for Panchayat Secretary Amount Request";
            String spApproved = "yes";
            updateData(s.getAadhar(), approved, status, spApproved);
        }
    }
    private void updateData(String aadharNumber, String approved,String status,String spApproved) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("status", status);
        individualInfo.put("ctrBenApproved", approved);
        individualInfo.put("spApproved", spApproved);
        db.collection("individuals").whereEqualTo("aadhar",aadharNumber)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("Hello","hi");
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentID=documentSnapshot.getId();
                    db.collection("individuals")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CollectorListOfBen2.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(CollectorListOfBen2.this, CollectorAction2.class);
//                                    intent.putExtra("village",village);
//                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CollectorListOfBen2.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(CollectorListOfBen2.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void push(ArrayList<SelectionElements> list) {
        selected=list;
        if(!selected.isEmpty())
        {
            checkAll.setVisibility(View.VISIBLE);
            cancelAll.setVisibility(View.VISIBLE);
        }else{
            checkAll.setVisibility(View.GONE);
            cancelAll.setVisibility(View.GONE);
        }
    }

    public void unitSearch(View view) {
        Intent i = new Intent(this, CollectorUnitSearch2.class);
        i.putExtra("district",district);
        //   i.putExtra("village",village);
        startActivity(i);
        finish();
    }
}

