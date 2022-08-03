package com.example.kmc.SPLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.CLogin.CollectorAmountDBToBen;
import com.example.kmc.Individual;
import com.example.kmc.List4;
import com.example.kmc.NoteElements;
import com.example.kmc.PSAdapters.myadapterPS2;
import com.example.kmc.R;
import com.example.kmc.SPAdapters.myadapterSP2;
import com.example.kmc.SelectionElements4;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.write.Label;

public class SPAmountToDB extends AppCompatActivity implements com.example.kmc.List4{

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    ArrayList<SelectionElements4> selected;

    myadapterSP2 adapter;
    String village1;

    Individual obj;
    String village2;
    List<DocumentSnapshot> list;
    ImageButton checkAll;
    ImageButton cancelAll;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spamount_to_db);
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
        adapter=new myadapterSP2(datalist,village1,village2,SPAmountToDB.this,SPAmountToDB.this);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {

                            obj=d.toObject(Individual.class);
                            if(!obj.getIndividualAmountRequired().equals("NA"))
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                    if(!obj.getSpApproved2().equals("yes") &&  !obj.getSpApproved2().equals("no"))
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
//        Intent i = new Intent(this, SpAmountToDbSearch.class);
//        i.putExtra("village1",village1);
//        i.putExtra("village2",village2);
//        startActivity(i);
    }

    public void checkAll(View view) {
        for(SelectionElements4 s:selected) {
            String approved="yes";
            String status=s.getApprovalAmount()+" approved by Special Officer to DB Account ";
            String psApproved="yes";
            if((Integer.parseInt(s.getApprovalAmount())+(Integer.parseInt(s.getBenAccountAmount())+(Integer.parseInt(s.getDbAccount()))))<=1000000)
            {
                updateData(s.getAadhar(),approved,status,psApproved,s.getApprovalAmount());
            }else{
                Toast.makeText(this, "Amount limit exceed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void cancelAll(View view) {
        for(SelectionElements4 s:selected) {
            String approved="no";
            String psApproved="NA";
            String status= "Rejected By SP: "+s.getStatus();
            updateData(s.getAadhar(),approved,status,psApproved,s.getBenAccountAmount());
        }
    }
    private void updateData(String aadharNumber, String approved,String status,String psApproved,String spApprovedAmount) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("spApproved2", approved.trim());
        individualInfo.put("sp_remarks", "Approved");
        individualInfo.put("ctrApproved", "NA");
        individualInfo.put("spAmountApproved", spApprovedAmount);
        individualInfo.put("psApproved", psApproved);
        individualInfo.put("status", status);
        individualInfo.put("ctrNote1", "NA");
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
                                //    Toast.makeText(SPAmountToDB.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SPAmountToDB.this, SPAmountToDB.class);
                                    intent.putExtra("village1",village1);
                                    intent.putExtra("village2",village2);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SPAmountToDB.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    Toast.makeText(SPAmountToDB.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
}