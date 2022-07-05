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
import com.example.kmc.PSAdapters.myadapter;
import com.example.kmc.SOAdapters.vendorAdapter;
import com.example.kmc.Vendor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SOAddEdit extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Vendor> datalist;
    FirebaseFirestore db;
    String sector;
    String mandal;
    ProgressBar progressBar;
    vendorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soadd_edit);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new vendorAdapter(datalist);
        recyclerView.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mandal");
            String value2 = extras.getString("sector");
            //String value3 = extras.getString("preferredUnit");
            //The key argument here must match that used in the other activity
            mandal = value;
            sector = value2;
            //preferredUnit = value3;

        }else{
            Log.d("extra", "no");
        }
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        db.collection("vendorAgency").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Vendor obj=d.toObject(Vendor.class);
                            if(!obj.getAgencyName().equalsIgnoreCase("others"))
                            {
                                datalist.add(obj);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void addIndividual(View view) {
//        Intent intent = new Intent(getApplicationContext(), addVendor.class);
//        intent.putExtra("village",village);
//        intent.putExtra("mandal",mandal);
//        intent.putExtra("sector",sector);
//        startActivity(intent);
//        finish();
        Intent i = new Intent(SOAddEdit.this, addVendor.class);
        i.putExtra("mandal",mandal);
        i.putExtra("sector",sector);
        startActivity(i);
    }
}