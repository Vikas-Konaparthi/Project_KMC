package com.example.kmc.SOLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kmc.Individual;
import com.example.kmc.PSLogin.PSAddEdit;
import com.example.kmc.PSLogin.PSAmountDBToBen;
import com.example.kmc.PSLogin.PSAmountToDB;
import com.example.kmc.PSLogin.PS_Action;
import com.example.kmc.PSLogin.password_change;
import com.example.kmc.R;
import com.example.kmc.login.Collector_Login;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Locale;

public class SO_Action extends AppCompatActivity implements View.OnClickListener{

    public CardView card1,card2,card3,card4;
    String mandal;
    String sector;
    String aadhar;
    int pendingAction1;
    TextView pendingBadge1;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_action);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        pendingBadge1=(TextView) findViewById(R.id.sopending1);
        db=FirebaseFirestore.getInstance();
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mandal");
            String value2 = extras.getString("sector");
            //String value3 = extras.getString("preferredUnit");
            //The key argument here must match that used in the other activity
            mandal = value;
            sector = value2;
            aadhar=extras.getString("aadhar");
            //preferredUnit = value3;

        }else{
            Log.d("extra", "no");
        }
        pendingAction1=0;
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getMandal().toLowerCase(Locale.ROOT).equals(mandal.toLowerCase(Locale.ROOT))) {
                                if(obj.getSpApproved3().equals("yes")) {
                                    if (obj.getPreferredUnit().toLowerCase(Locale.ROOT).equals(sector.toLowerCase(Locale.ROOT))) {
                                        if(!obj.getSoApproved().equals("yes") &&  !obj.getSoApproved().equals("no"))
                                        {
                                            pendingAction1=pendingAction1+1;
                                        }
                                    }
                                }
                            }
                        }
                        pendingBadge1.setText(String.valueOf(pendingAction1));
                    }
                });

    }
    @Override
    public void onRestart() {
        super.onRestart();
        pendingAction1=0;
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getMandal().toLowerCase(Locale.ROOT).equals(mandal.toLowerCase(Locale.ROOT))) {
                                if(obj.getSpApproved3().equals("yes")) {
                                    if (obj.getPreferredUnit().toLowerCase(Locale.ROOT).equals(sector.toLowerCase(Locale.ROOT))) {
                                        if(!obj.getSoApproved().equals("yes") &&  !obj.getSoApproved().equals("no"))
                                        {
                                            pendingAction1=pendingAction1+1;
                                        }
                                    }
                                }
                            }
                        }
                        pendingBadge1.setText(String.valueOf(pendingAction1));
                    }
                });
    }
    public void changePass(View view) {
        Intent intent = new Intent(SO_Action.this, password_change_so.class);
        intent.putExtra("sector",sector);
        intent.putExtra("mandal",mandal);
        intent.putExtra("aadhar",aadhar);
        SO_Action.this.startActivity(intent);
    }
    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.c1:
                i = new Intent(this, SOAddEdit.class);
                i.putExtra("mandal",mandal);
                i.putExtra("sector",sector);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, SOAmountDBToBen.class);
                i.putExtra("mandal",mandal);
                i.putExtra("sector",sector);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, SOGrounding.class);
                i.putExtra("mandal",mandal);
                i.putExtra("sector",sector);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, SOMasterReport.class);
                i.putExtra("mandal",mandal);
                i.putExtra("sector",sector);
                startActivity(i);
                break;
        }


    }
}