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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CollectorMenu extends AppCompatActivity {
    FirebaseFirestore db;
    String district;
    String selected_village;
    String selected_mandal;
    String selected_constitutiency;
    TextView mandal_pending;
    TextView village_pending;
    TextView spMandal;
    TextView spVillage;
    TextView psVillage;
    TextView groundingVillage;
    TextView groundingMandal;
    TextView psMandal;
    TextView total_no_registered;
    TextView total_no_sanctioned;
    TextView total_no_released;
    TextView partially_grounded;
    int total_registered;
    int total_sanctioned;
    int total_released;
    int partially_g;
    int fully_g;
    int ctrMandalPending;
    int ctrVillagePending;
    int spMandalPending;
    int spVillagePending;
    int psMandalPending;
    int psVillagePending;
    int groundingVillagePending;
    int groundingMandalPending;
    int ctrConstituencyPending;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_menu);
        total_no_registered=(TextView) findViewById(R.id.t2);
        total_no_sanctioned=(TextView) findViewById(R.id.t4);
        total_no_released=(TextView) findViewById(R.id.t6);
        partially_grounded=(TextView) findViewById(R.id.t8);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = rootRef.collection("Khammam");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district = extras.getString("district");
        }
        Spinner spinnerConstituency = (Spinner) findViewById(R.id.spinner_constituency);
        Spinner spinnerMandal = (Spinner) findViewById(R.id.spinner_mandal);
        Spinner spinnerVillage=(Spinner) findViewById(R.id.spinner_village);
        List<String> constituencies = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                constituencies);
        List<String> mandals = new ArrayList<>();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                mandals);
        List<String> villages = new ArrayList<>();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                villages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConstituency.setAdapter(adapter);
        spinnerMandal.setAdapter(adapter1);
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
                            if(obj.getSpApproved().equals("yes"))
                            {
                                total_sanctioned=total_sanctioned+1;
                            }
                            if(!obj.getApprovalAmount().equals("0"))
                            {
                                total_released=total_released+1;
                            }
                            if((Integer.parseInt(obj.getApprovalAmount())>0 && Integer.parseInt(obj.getApprovalAmount())<990000)||(Integer.parseInt(obj.getApprovalAmount())>=990000))
                            {
                                partially_g=partially_g+1;
                            }
                            total_registered=total_registered+1;
                        }
                        total_no_registered.setText(String.valueOf(total_registered));
                        total_no_sanctioned.setText(String.valueOf(total_sanctioned));
                        total_no_released.setText(String.valueOf(total_released));
                        partially_grounded.setText(String.valueOf(partially_g));
                    }
                });



        db.collection(district+"_constituencies").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {

                            District obj = d.toObject(District.class);

                            obj.setUid(d.getId().toString());
                            db.collection("individuals").whereEqualTo("constituency",obj.getUid()).get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot d : list) {
                                                Individual objCtrMP = d.toObject(Individual.class);
                                                if ((objCtrMP.getSpApproved2().equals("yes") && objCtrMP.getCtrApproved().equals("")) || (objCtrMP.getSpApproved3().equals("yes") && objCtrMP.getCtrApproved2().equals(""))) {
                                                    ctrConstituencyPending = ctrConstituencyPending + 1;
                                                }
                                              }

                                            constituencies.add(obj.getUid()+" ("+ctrConstituencyPending+")");
                                            adapter.notifyDataSetChanged();
                                            selected_constitutiency=adapter.getItem(0);
                                            ctrConstituencyPending=0;
                                        }
                                    });
                        }
                    }
                });
        spinnerConstituency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                mandals.clear();
                spinnerConstituency.setSelection(position);
                selected_constitutiency = spinnerConstituency.getSelectedItem().toString();

                db.collection(district).whereEqualTo("constituency",selected_constitutiency.substring(0,selected_constitutiency.indexOf(" "))).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot d:list) {
                                    District obj = d.toObject(District.class);

                                    obj.setUid(d.getId().toString());
                                    db.collection("individuals").whereEqualTo("mandal",obj.getUid()).get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    for (DocumentSnapshot d : list) {
                                                        Individual objCtrMP = d.toObject(Individual.class);
                                                        if ((objCtrMP.getSpApproved2().equals("yes") && objCtrMP.getCtrApproved().equals("")) || (objCtrMP.getSpApproved3().equals("yes") && objCtrMP.getCtrApproved2().equals(""))) {
                                                            ctrMandalPending = ctrMandalPending + 1;
                                                        }
                                                    }
                                                    mandals.add(obj.getUid()+" ("+ctrMandalPending+")");
                                                    adapter1.notifyDataSetChanged();
                                                    selected_mandal=adapter.getItem(0);
                                                    ctrMandalPending=0;
                                                }
                                            });

                                }

                            }

                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        spinnerMandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                villages.clear();
                spinnerVillage.setVisibility(View.VISIBLE);
                spinnerMandal.setSelection(position);
                selected_mandal = spinnerMandal.getSelectedItem().toString();

                db.collection(district).document(selected_mandal.substring(0,selected_mandal.indexOf(" "))).collection("villages").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot d:list) {
                                    Mandals obj = d.toObject(Mandals.class);
                                    db.collection("individuals").whereEqualTo("village",obj.getVillage()).get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    Log.d("HELLO","HI");
                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    for (DocumentSnapshot d : list) {
                                                        Individual objCtrMP = d.toObject(Individual.class);
                                                        if ((objCtrMP.getSpApproved2().equals("yes") && objCtrMP.getCtrApproved().equals("")) || (objCtrMP.getSpApproved3().equals("yes") && objCtrMP.getCtrApproved2().equals(""))) {
                                                            ctrVillagePending = ctrVillagePending + 1;
//                                                           final String ctr=String.valueOf(ctrMandalPending);
//                                                            Log.d("HELLO",ctr);
//                                                            Log.d("tag",String.valueOf(ctrMandalPending));
//                                                            villages.add(obj.getVillage()+" "+ctrMandalPending);
//                                                            adapter2.notifyDataSetChanged();
//                                                            selected_village=adapter2.getItem(0);
//                                                            ctrMandalPending=0;
                                                        }
//                                                        Mandals obj = d.toObject(Mandals.class);
//                                                        villages.add(obj.getVillage()+ String.valueOf(ctrMandalPending));
                                                    }
                                                    villages.add(obj.getVillage()+" ("+ctrVillagePending+")");
                                                    adapter2.notifyDataSetChanged();
                                                    selected_village=adapter2.getItem(0);
                                                    ctrVillagePending=0;
//                                                  adapter2.notifyDataSetChanged();
//                                                    selected_village=adapter2.getItem(0);
                                                }
                                            });
//                                   Mandals obj = d.toObject(Mandals.class);
//                                    Log.d("tag",String.valueOf(ctrMandalPending));
//                                    villages.add(obj.getVillage()+"-"+ctrMandalPending);
                                }
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
//                selected_village.replaceAll("\\s+","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

//        Toast.makeText(this, selected_mandal+","+selected_village, Toast.LENGTH_SHORT).show();

    }
    public void next(View view) {
        Intent i = new Intent(this, CollectorAction.class);
        i.putExtra("village",selected_village.substring(0,selected_village.indexOf(" ")));
        startActivity(i);
    }

    public void overview(View view) {
        Intent i = new Intent(this, CollectorDistrictOverview.class);
        i.putExtra("district",district);
        startActivity(i);
    }
}