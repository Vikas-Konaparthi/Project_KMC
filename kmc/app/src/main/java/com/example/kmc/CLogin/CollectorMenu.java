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
    TextView total_no_registered;
    TextView total_no_sanctioned;
    TextView total_no_released;
    TextView partially_grounded;
    TextView fully_grounded;
    int total_registered;
    int total_sanctioned;
    int total_released;
    int partially_g;
    int fully_g;
    int c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_menu);
        total_no_registered=(TextView) findViewById(R.id.t2);
        total_no_sanctioned=(TextView) findViewById(R.id.t4);
        total_no_released=(TextView) findViewById(R.id.t6);
        partially_grounded=(TextView) findViewById(R.id.t8);
        fully_grounded=(TextView) findViewById(R.id.t10);
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
        total_registered=0;
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getDbAccount()!="")
                            {
                                total_sanctioned=total_sanctioned+1;
                            }
                            if(obj.getApprovalAmount()!="")
                            {
                                total_released=total_released+1;
                            }
                            if(Integer.parseInt(obj.getApprovalAmount())<990000)
                            {
                                partially_g=partially_g+1;
                            }
                            if(Integer.parseInt(obj.getApprovalAmount())>=990000)
                            {
                                fully_g=fully_g+1;
                            }

                            total_registered=total_registered+1;
                        }
                        total_no_registered.setText(String.valueOf(total_registered));
                        total_no_sanctioned.setText(String.valueOf(total_sanctioned));
                        total_no_released.setText(String.valueOf(total_released));
                        partially_grounded.setText(String.valueOf(partially_g));
                        fully_grounded.setText(String.valueOf(fully_g));
                    }
                });


        db.collection(district).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {

                            District obj = d.toObject(District.class);
                            c=0;
                            obj.setUid(d.getId().toString());
                            db.collection("individuals").whereEqualTo("mandal",obj.getUid()).whereEqualTo("ctrApproved2","").whereEqualTo("ctrApproved","").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                c=queryDocumentSnapshots.size();
                                                Log.d("Lenght",String.valueOf(c));
                                        }
                                    });

                            mandals.add(obj.getUid()+" ("+c+")");
                        }
                        adapter.notifyDataSetChanged();
                    }

                });
        spinnerMandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                villages.clear();
                spinnerVillage.setVisibility(View.VISIBLE);
                spinnerMandal.setSelection(position);
                selected_mandal = spinnerMandal.getSelectedItem().toString().split(" ")[0];

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
