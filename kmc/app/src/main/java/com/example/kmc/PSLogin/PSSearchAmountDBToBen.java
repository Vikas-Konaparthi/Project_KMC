package com.example.kmc.PSLogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.Individual;
import com.example.kmc.PSAdapters.myadapterPS2;
import com.example.kmc.PSAdapters.myadapterPS3;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PSSearchAmountDBToBen extends AppCompatActivity {
    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    public TextInputEditText searchBox;

    String district;
    ProgressBar progressBar;
    String searchText;
    String village;

    myadapterPS3 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBox=findViewById(R.id.searchbox);
        datalist=new ArrayList<>();
        searchText="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district= extras.getString("district");
            village=extras.getString("village");
        }
        adapter=new myadapterPS3(datalist);
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
                searchText=searchBox.getText().toString();
            }
        });

    }

    public void searchbutton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("searchText",searchText);
        db.collection("individuals").orderBy("name").startAt(searchText).endAt(searchText.toLowerCase(Locale.ROOT)+"\uf8ff").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        datalist.clear();
                        for(DocumentSnapshot d:list)
                        {

                            Individual obj=d.toObject(Individual.class);
                            if(obj.getVillage()!=null)
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).toString().equals(village.toLowerCase(Locale.ROOT)));
                                {
                                    if(obj.getCtrApproved().toString().equalsIgnoreCase("yes"))
                                    {
                                        if(!obj.getPsApproved2().equals("yes")){
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
    }

}