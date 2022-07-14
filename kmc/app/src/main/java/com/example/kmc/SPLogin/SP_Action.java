package com.example.kmc.SPLogin;

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
import com.example.kmc.PSLogin.PSMasterReport;
import com.example.kmc.PSLogin.PS_Action;
import com.example.kmc.PSLogin.password_change;
import com.example.kmc.R;
import com.example.kmc.login.Collector_Login;
import com.example.kmc.login.SOLogin;
import com.example.kmc.login.SPLogin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Locale;

public class SP_Action extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4,card5;
    String village1;
    String village2;
    String aadhar;
    int pendingAction1;
    int pendingAction2;
    int pendingAction3;
    TextView pendingBadge1;
    TextView pendingBadge2;
    TextView pendingBadge3;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_action);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        card5 = (CardView) findViewById(R.id.c5);
        pendingBadge1=(TextView) findViewById(R.id.sppending1);
        pendingBadge2=(TextView) findViewById(R.id.sppending2);
        pendingBadge3=(TextView) findViewById(R.id.sppending3);
        db= FirebaseFirestore.getInstance();

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village1");
            String value2 = extras.getString("village2");
            aadhar = extras.getString("aadhar");
            //The key argument here must match that used in the other activity
            village1 = value;
            village2 = value2;
        }else{
            Log.d("extra", "no");
        }
        pendingAction1=0;
        pendingAction2=0;
        pendingAction3=0;
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                if(!obj.getSpApproved().equals("yes"))
                                {
                                    pendingAction1=pendingAction1+1;
                                }
                            }
                            if(!obj.getPsRequestedAmountToBeneficiary().equals("NA") && obj.getSoApproved().equals("yes"))
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                    if(!obj.getSpApproved3().equals("yes") &&  !obj.getSpApproved3().equals("no"))
                                    {
                                        pendingAction3=pendingAction3+1;
                                    }
                                }
                            }
                            if(!obj.getIndividualAmountRequired().equals("NA"))
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                    if(!obj.getSpApproved2().equals("yes") &&  !obj.getSpApproved2().equals("no"))
                                    {
                                        pendingAction2=pendingAction2+1;
                                    }
                                }
                            }
                        }
                        pendingBadge1.setText(String.valueOf(pendingAction1));
                        pendingBadge2.setText(String.valueOf(pendingAction2));
                        pendingBadge3.setText(String.valueOf(pendingAction3));
                    }
                });
    }
    @Override
    public void onRestart() {
        super.onRestart();
        pendingAction1=0;
        pendingAction2=0;
        pendingAction3=0;
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                if(!obj.getSpApproved().equals("yes"))
                                {
                                    pendingAction1=pendingAction1+1;
                                }
                            }
                            if(!obj.getPsRequestedAmountToBeneficiary().equals("NA") && obj.getSoApproved().equals("yes"))
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                    if(!obj.getSpApproved3().equals("yes") &&  !obj.getSpApproved3().equals("no"))
                                    {
                                        pendingAction3=pendingAction3+1;
                                    }
                                }
                            }
                            if(!obj.getIndividualAmountRequired().equals("NA"))
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                    if(!obj.getSpApproved2().equals("yes") &&  !obj.getSpApproved2().equals("no"))
                                    {
                                        pendingAction2=pendingAction2+1;
                                    }
                                }
                            }
                        }
                        pendingBadge1.setText(String.valueOf(pendingAction1));
                        pendingBadge2.setText(String.valueOf(pendingAction2));
                        pendingBadge3.setText(String.valueOf(pendingAction3));
                    }
                });

    }
    public void changePassword(View view) {
        Intent intent = new Intent(SP_Action.this, password_change_sp.class);
        intent.putExtra("village1",village1);
        intent.putExtra("village2",village2);
        intent.putExtra("aadhar",aadhar);
        SP_Action.this.startActivity(intent);
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
                i = new Intent(this, ListOfBen.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, SPAmountToDB.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, SPAmountDBToBen.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, SPGrounding.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
            case R.id.c5:
                i = new Intent(this, SPMasterReport.class);
                i.putExtra("village1",village1);
                i.putExtra("village2",village2);
                startActivity(i);
                break;
        }


    }
}