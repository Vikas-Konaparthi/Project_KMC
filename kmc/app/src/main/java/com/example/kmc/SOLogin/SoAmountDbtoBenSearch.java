package com.example.kmc.SOLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kmc.CollectorAdapters.myadapter4;
import com.example.kmc.CollectorAdapters.searchAdapterCollector;
import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.SOAdapters.myadapter3SO2;
import com.example.kmc.SOAdapters.vendorAdapter;
import com.example.kmc.Vendor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SoAmountDbtoBenSearch extends AppCompatActivity {
    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    public TextInputEditText searchBox;

    String mandal;
    ProgressBar progressBar;
    String searchText;
    String sector;
    myadapter3SO2 adapter;
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
            mandal= extras.getString("mandal");
            sector=extras.getString("sector");
        }
        adapter=new myadapter3SO2(datalist,mandal,sector);
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
                searchText=searchBox.getText().toString().toLowerCase();
            }
        });

    }
    public void searchbutton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("individuals").orderBy("name").startAt(searchText).endAt(searchText+"\uf8ff").get()
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
    }

}
