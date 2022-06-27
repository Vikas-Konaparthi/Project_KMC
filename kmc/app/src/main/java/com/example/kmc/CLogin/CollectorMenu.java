package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kmc.District;
import com.example.kmc.Individual;
import com.example.kmc.Mandals;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CollectorMenu extends AppCompatActivity {
    FirebaseFirestore db;
    String district;
    String selected_village;
    String selected_mandal;
    TextView mandal_pending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_menu);
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = rootRef.collection("Khammam");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district = extras.getString("district");
        }
        Spinner spinnerMandal = (Spinner) findViewById(R.id.spinner_mandal);
        Spinner spinnerVillage=(Spinner) findViewById(R.id.spinner_village);
        List<String> mandals = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                mandals);
        List<String> villages = new ArrayList<>();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                villages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMandal.setAdapter(adapter);
        spinnerVillage.setAdapter(adapter2);
        db=FirebaseFirestore.getInstance();
        db.collection(district).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {

                            District obj = d.toObject(District.class);

                            obj.setUid(d.getId().toString());
                            mandals.add(obj.getUid());
                        }
                        adapter.notifyDataSetChanged();
                    }

                });
        spinnerMandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {

                spinnerVillage.setVisibility(View.VISIBLE);
                spinnerMandal.setSelection(position);
                selected_mandal = spinnerMandal.getSelectedItem().toString();

                db.collection(district).document(selected_mandal).collection("villages").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot d:list) {
                                    Mandals obj = d.toObject(Mandals.class);
                                    villages.add(obj.getVillage());
                                }
                                adapter2.notifyDataSetChanged();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {

                spinnerVillage.setSelection(position);
                selected_village = spinnerVillage.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
//        Toast.makeText(this, selected_mandal+","+selected_village, Toast.LENGTH_SHORT).show();

    }
    public void next(View view) {
        Intent i = new Intent(this, CollectorAction.class);
        i.putExtra("village",selected_village);
        startActivity(i);
    }
}

