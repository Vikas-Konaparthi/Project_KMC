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

import com.example.kmc.CollectorAdapters.myadapterUnitSearch3;
import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.SelectionElements;
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

public class CollectorUnitSearch3 extends AppCompatActivity implements com.example.kmc.List{
    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    public TextInputEditText searchBox;
    ArrayList<SelectionElements> selected;
    String district;
    ProgressBar progressBar;
    String searchText;
    String village;
    myadapterUnitSearch3 adapter;
    ImageButton checkAll;
    ImageButton cancelAll;
    LinearLayout l1;
    TextInputLayout searchboxLayout;
    View v;
    ImageButton searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBox=findViewById(R.id.searchbox);
        searchButton=findViewById(R.id.searchbutton);
        searchboxLayout=findViewById(R.id.searchboxLayout);
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
        adapter=new myadapterUnitSearch3(datalist,district,CollectorUnitSearch3.this,CollectorUnitSearch3.this);
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
                                if (obj.getSpApproved2().equals("yes")) {
                                    if (!obj.getCtrApproved().equals("yes") && !obj.getCtrApproved().equals("no"))
                                    datalist.add(obj);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    public void checkAll(View view) {
        for(SelectionElements s:selected)
        {
            String creditedToDB= String.valueOf(Integer.parseInt(s.getCreditedToDB())+Integer.parseInt(s.getApprovedAmount()));
            String collectorSanction= String.valueOf(Integer.parseInt(s.getDbAccountAmount())+Integer.parseInt(s.getApprovedAmount()));
            Toast.makeText(this, creditedToDB+" "+s.getApprovedAmount()+" "+s.getDbAccountAmount(), Toast.LENGTH_SHORT).show();
            Log.d("Heloooooooooo",creditedToDB+" "+s.getApprovedAmount()+" "+s.getDbAccountAmount());
            updateData(s.getAadhar(),"yes",s.getApprovedAmount()+" Credited to DB Account",collectorSanction,creditedToDB,"yes");
        }
        Toast.makeText(this, "Approved", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void updateData(String aadharNumber, String approved,String status,String collectorSanctionAmount,String creditedToDB,String spApproved) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("status", status);
        individualInfo.put("ctrApproved", approved);
        individualInfo.put("spApproved2", spApproved);
        individualInfo.put("dbAccount", collectorSanctionAmount);
        individualInfo.put("creditedToDB", creditedToDB);
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
                                    Toast.makeText(CollectorUnitSearch3.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(CollectorAmountToDB.this, CollectorAmountToDB.class);
//                                    intent.putExtra("village",village);
//                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CollectorUnitSearch3.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(CollectorUnitSearch3.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void push(ArrayList<SelectionElements> list) {
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