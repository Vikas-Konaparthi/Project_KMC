package com.example.kmc.CLogin;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.CollectorAdapters.myadapterUnitSearch4;
import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.SelectionElements;
import com.example.kmc.SelectionElements2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CollectorUnitSearch4 extends AppCompatActivity implements com.example.kmc.List2 {
    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    public TextInputEditText searchBox;
    ArrayList<SelectionElements2> selected;
    String district;
    ProgressBar progressBar;
    String searchText;
    String village;
    myadapterUnitSearch4 adapter;
    ImageButton checkAll;
    ImageButton cancelAll;
    LinearLayout l1;
    View v;
    TextInputLayout searchboxLayout;
    ImageButton searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBox=findViewById(R.id.searchbox);
        searchboxLayout=findViewById(R.id.searchboxLayout);
        searchButton=findViewById(R.id.searchbutton);
        datalist=new ArrayList<>();
        checkAll=findViewById(R.id.checkAll);
        cancelAll=findViewById(R.id.cancelAll);
        v=findViewById(R.id.view);
        l1=findViewById(R.id.layout1);
        searchText="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district= extras.getString("district");
         //   village=extras.getString("village");
        }
        adapter=new myadapterUnitSearch4(datalist,district,CollectorUnitSearch4.this,CollectorUnitSearch4.this);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText=searchBox.getText().toString().toLowerCase(Locale.ROOT);
            }
        });

    }

    public void searchbutton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("searchtext",searchText);
        db.collection("individuals").orderBy("preferredUnit").startAt(searchText).endAt(searchText+"\uf8ff").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        datalist.clear();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            Log.d("searchtext2",obj.getPreferredUnit());
                            if(obj.getDistrict().equalsIgnoreCase(district))
                            {
                                if (obj.getSpApproved3().equals("yes") && obj.getSoApproved().equals("yes"))
                                    if (!obj.getCtrApproved2().equals("yes") && !obj.getCtrApproved2().equals("no")){
                                        datalist.add(obj);
                                    }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    public void cancelAll(View view) {
        for (SelectionElements2 s : selected) {
            String collectorSanctionAmount = s.getDbAccount();
            String approved = "no";
            String soApproved = "yes";
            String spApproved = "NA";
            String status = "Rejected By Collector: " + s.getStatus();
            int approvalAmount = Integer.parseInt(s.getBenAccountAmount());
            updateData(s.getAadhar(), approved, status, collectorSanctionAmount, Integer.toString(approvalAmount), soApproved, spApproved);
        }
    }
    public void checkAll(View view) {
        for(SelectionElements2 s:selected)
        {
            int updateDBAccount=Integer.parseInt(s.getDbAccount())-Integer.parseInt(s.getSoApprovalAmount());
            String updateAmount=Integer.toString(updateDBAccount);
            int approvalAmount=Integer.parseInt(s.getBenAccountAmount())+Integer.parseInt(s.getSoApprovalAmount());
            updateData(s.getAadhar(),"yes",approvalAmount+" released to beneficiary account.",updateAmount,String.valueOf(approvalAmount),"yes","yes");
        }
        Toast.makeText(this, "Approved", Toast.LENGTH_SHORT).show();
        finish();
    }
        private void updateData(String aadharNumber, String approved,String status,String collectorSanctionAmount,String approvalAmount,String soApproved,String spApproved) {
            Map<String, Object> individualInfo = new HashMap<String, Object>();
            individualInfo.put("status", status);
            individualInfo.put("status", status);
            individualInfo.put("ctrApproved2", approved);
            individualInfo.put("dbAccount", collectorSanctionAmount);
            individualInfo.put("approvalAmount", approvalAmount);
            individualInfo.put("soApproved", soApproved);
            individualInfo.put("spApproved3", spApproved);

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
                                        //                               Toast.makeText(CollectorAmountDBToBen2.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(CollectorAmountDBToBen2.this, CollectorAmountDBToBen2.class);
//                                            intent.putExtra("village",village);
//                                            startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CollectorUnitSearch4.this, "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{

                        Toast.makeText(CollectorUnitSearch4.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }


    @Override
    public void push(ArrayList<SelectionElements2> list) {
        selected=list;
        if(!selected.isEmpty())
        {
            l1.setBackgroundColor(Color.parseColor("#6200EE"));
            v.setVisibility(View.VISIBLE);
            checkAll.setVisibility(View.VISIBLE);
            cancelAll.setVisibility(View.VISIBLE);
            searchboxLayout.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
        }else{
            l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            v.setVisibility(View.GONE);
            checkAll.setVisibility(View.GONE);
            cancelAll.setVisibility(View.GONE);
            searchboxLayout.setVisibility(View.VISIBLE);
            searchButton.setVisibility(View.VISIBLE);
        }
    }
}