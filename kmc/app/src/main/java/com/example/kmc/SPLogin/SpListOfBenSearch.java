package com.example.kmc.SPLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.kmc.CollectorAdapters.myadapter4;
import com.example.kmc.CollectorAdapters.searchAdapterCollector;
import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.SPAdapters.myadapter2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SpListOfBenSearch extends AppCompatActivity {
    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    String searchText;
    myadapter2 adapter;
    String village1;
    String village2;
    ProgressBar progressBar;
    public TextInputEditText searchBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBox=findViewById(R.id.searchbox);
        datalist=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village1");
            String value2 = extras.getString("village2");
            //The key argument here must match that used in the other activity
            village1 = value;
            village2 = value2;
        }
        adapter=new myadapter2(datalist,village1,village2);
        recyclerView.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        db=FirebaseFirestore.getInstance();

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
    public void searchbutton(View view){
        progressBar.setVisibility(View.VISIBLE);
        db.collection("individuals").orderBy("name").startAt(searchText).endAt(searchText+"\uf8ff").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        datalist.clear();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                if(!obj.getSpApproved().equals("yes"))
                                {
                                    datalist.add(obj);
                                }

                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}
