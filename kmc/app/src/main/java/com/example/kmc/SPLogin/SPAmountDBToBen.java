package com.example.kmc.SPLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.SPAdapters.myadapterSP2;
import com.example.kmc.SPAdapters.myadapterSP3;
import com.example.kmc.SelectionElements4;
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

public class SPAmountDBToBen extends AppCompatActivity implements com.example.kmc.List4{

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;

    ArrayList<SelectionElements4> selected;
    myadapterSP3 adapter;
    String village1;
    String village2;
    ImageButton checkAll;
    ImageButton cancelAll;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spamount_dbto_ben);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkAll=findViewById(R.id.checkAll);
        cancelAll=findViewById(R.id.cancelAll);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village1");
            String value2 = extras.getString("village2");
            //The key argument here must match that used in the other activity
            village1 = value;
            village2 = value2;
        }
        datalist=new ArrayList<>();
        adapter=new myadapterSP3(datalist,village1,village2,SPAmountDBToBen.this,SPAmountDBToBen.this);
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
                            if(!obj.getPsApprovedAmount().equals("NA") && obj.getSoApproved().equals("yes"))
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                    if(!obj.getSpApproved3().equals("yes") &&  !obj.getSpApproved3().equals("no"))
                                    {
                                        datalist.add(obj);
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
//        Intent i = new Intent(this, SpDbToBenSearch.class);
//        i.putExtra("village1",village1);
//        i.putExtra("village2",village2);
//        startActivity(i);
    }
    @Override
    public void push(ArrayList<SelectionElements4> list) {
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
    public void checkAll(View view) {
        for(SelectionElements4 s:selected) {
            String approved="yes";
            String psApproved="yes";
            String status=s.getApprovalAmount()+" approved by Special Officer waiting for Collector Approval";
            updateData(s.getAadhar(),approved,status,psApproved);
        }
    }
    public void cancelAll(View view) {
        for(SelectionElements4 s:selected) {
            String approved="no";
            String psApproved="NA";
            String status= "Rejected By SP: "+s.getStatus();
            updateData(s.getAadhar(),approved,status,psApproved);
        }
    }

    private void updateData(String aadharNumber, String approved,String status,String psApproved) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("spApproved3", approved.trim());
        individualInfo.put("sp_remarks", "Approved");
        individualInfo.put("psApproved2",psApproved);
        individualInfo.put("ctrApproved2","NA");
        individualInfo.put("status", status);
        individualInfo.put("ctrNote2", "NA");
        Toast.makeText(this, aadharNumber, Toast.LENGTH_SHORT).show();
        db.collection("individuals").whereEqualTo("aadhar",aadharNumber)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentID=documentSnapshot.getId();
                    db.collection("individuals")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SPAmountDBToBen.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SPAmountDBToBen.this, SPAmountDBToBen.class);
                                    intent.putExtra("village1",village1);
                                    intent.putExtra("village2",village2);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SPAmountDBToBen.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    Toast.makeText(SPAmountDBToBen.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}