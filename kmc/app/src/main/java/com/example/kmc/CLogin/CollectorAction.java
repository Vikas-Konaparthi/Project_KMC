package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kmc.Individual;
import com.example.kmc.PSLogin.PSAddEdit;
import com.example.kmc.PSLogin.PSAmountToDB;
import com.example.kmc.PSLogin.PS_Action;
import com.example.kmc.PSLogin.password_change;
import com.example.kmc.R;
import com.example.kmc.SPLogin.ListOfBen;
import com.example.kmc.login.Collector_Login;
import com.example.kmc.login.SOLogin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Locale;

public class CollectorAction extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4,card5,card6;
    String village;
    int pendingAction1;
    int pendingAction2;
    String aadhar;
    FirebaseFirestore db;
    TextView pendingBadge1;
    TextView pendingBadge2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_action);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        card5 = (CardView) findViewById(R.id.c5);
        card6 = (CardView) findViewById(R.id.c6);

        pendingBadge1=(TextView) findViewById(R.id.cpending1);
        pendingBadge2=(TextView) findViewById(R.id.cpending2);
        db=FirebaseFirestore.getInstance();
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            village = extras.getString("village");

        }else{
            Log.d("extra", "no");
        }
        pendingAction1=0;
        pendingAction2=0;

        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village.toLowerCase(Locale.ROOT))) {
                                if (obj.getSpApproved2().equals("yes")) {
                                    if (!obj.getCtrApproved().equals("yes") && !obj.getCtrApproved().equals("no")) {
                                        pendingAction1=pendingAction1+1;
                                    }
                                }
                            }
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village.toLowerCase(Locale.ROOT)))
                            {
                                if(obj.getSpApproved3().equals("yes") && obj.getSoApproved().equals("yes"))
                                    if(!obj.getCtrApproved2().equals("yes") &&  !obj.getCtrApproved2().equals("no")) {
                                        pendingAction2=pendingAction2+1;
                                    }
                            }
                        }
                        pendingBadge1.setText(String.valueOf(pendingAction1));
                        pendingBadge2.setText(String.valueOf(pendingAction2));
                    }
                });



    }
    @Override
    public void onRestart() {
        super.onRestart();
        pendingAction1=0;
        pendingAction2=0;
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village.toLowerCase(Locale.ROOT))) {
                                if (obj.getSpApproved2().equals("yes")) {
                                    if (!obj.getCtrApproved().equals("yes") && !obj.getCtrApproved().equals("no")) {
                                        pendingAction1=pendingAction1+1;
                                    }
                                }
                            }
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village.toLowerCase(Locale.ROOT)))
                            {
                                if(obj.getSpApproved3().equals("yes") && obj.getSoApproved().equals("yes"))
                                    if(!obj.getCtrApproved2().equals("yes") &&  !obj.getCtrApproved2().equals("no")) {
                                        pendingAction2=pendingAction2+1;
                                    }
                            }
                        }
                        pendingBadge1.setText(String.valueOf(pendingAction1));
                        pendingBadge2.setText(String.valueOf(pendingAction2));
                    }
                });
    }
    public  void changePass(View view)
    {
        Intent intent = new Intent(CollectorAction.this, change_password_ctr.class);
        intent.putExtra("village",village);
        intent.putExtra("aadhar",aadhar);
        CollectorAction.this.startActivity(intent);
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
                i = new Intent(this, CollectorListOfBen.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, CollectorAmountToDB.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, CollectorAmountDBToBen.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, CollectorGrounding.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c5:
                i = new Intent(this, CollectorMasterReport.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
            case R.id.c6:
                i = new Intent(this, CollectorDistrictMasterReport.class);
                i.putExtra("village",village);
                startActivity(i);
                break;
        }
    }
}