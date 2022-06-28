package com.example.kmc.PSLogin;

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

public class PS_Action extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4, card5;
    String village;
    String district;
    String mandal;
    String uname;
    String aadhar;
    int pendingAction1;
    int pendingAction2;
    int pendingAction3;
    FirebaseFirestore db;
    TextView pendingBadge1;
    TextView pendingBadge2;
    TextView pendingBadge3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_action);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        card5 = (CardView) findViewById(R.id.c5);
        pendingBadge1=(TextView) findViewById(R.id.pending1);
        pendingBadge2=(TextView) findViewById(R.id.pending2);
        pendingBadge3=(TextView) findViewById(R.id.pending3);
        db=FirebaseFirestore.getInstance();
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village");
            village=value;
            mandal=extras.getString("mandal");
            district=extras.getString("district");
            uname=extras.getString("uname");
            aadhar=extras.getString("aadhar");
            //The key argument here must match that used in the other activity
        }else{
            Log.d("extra", "no");
        }
        Individual obj = new Individual();
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
                            if(obj.getVillage()!=null)
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).toString().equals(village.toLowerCase(Locale.ROOT)));
                                {
                                    if(obj.getSpApproved().toString().equalsIgnoreCase("yes"))
                                    {
                                        if(!obj.getPsApproved().equals("yes")) {
                                            pendingAction1=pendingAction1+1;
                                            Log.d("Hello","H");
                                            Log.d("Hello",String.valueOf(pendingAction1));
                                        }
                                    }
                                }
                            }
                            if(obj.getVillage()!=null)
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).toString().equals(village.toLowerCase(Locale.ROOT)));
                                {
                                    if(obj.getCtrApproved2().toString().equalsIgnoreCase("yes"))
                                    {
                                        pendingAction3=pendingAction3+1;
                                    }
                                }
                            }
                            if(obj.getVillage()!=null)
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).toString().equals(village.toLowerCase(Locale.ROOT)));
                                {
                                    if(obj.getCtrApproved().toString().equalsIgnoreCase("yes"))
                                    {
                                        if(!obj.getPsApproved2().equals("yes")){
                                            pendingAction2=pendingAction2+1;
                                        }
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
                            if(obj.getVillage()!=null)
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).toString().equals(village.toLowerCase(Locale.ROOT)));
                                {
                                    if(obj.getSpApproved().toString().equalsIgnoreCase("yes"))
                                    {
                                        if(!obj.getPsApproved().equals("yes")) {
                                            pendingAction1=pendingAction1+1;
                                            Log.d("Hello","H");
                                            Log.d("Hello",String.valueOf(pendingAction1));
                                        }
                                    }
                                }
                            }
                            if(obj.getVillage()!=null)
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).toString().equals(village.toLowerCase(Locale.ROOT)));
                                {
                                    if(obj.getCtrApproved2().toString().equalsIgnoreCase("yes"))
                                    {
                                        pendingAction3=pendingAction3+1;
                                    }
                                }
                            }
                            if(obj.getVillage()!=null)
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).toString().equals(village.toLowerCase(Locale.ROOT)));
                                {
                                    if(obj.getCtrApproved().toString().equalsIgnoreCase("yes"))
                                    {
                                        if(!obj.getPsApproved2().equals("yes")){
                                            pendingAction2=pendingAction2+1;
                                        }
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

    public void changePass(View view) {
        Intent intent = new Intent(PS_Action.this, password_change.class);
        intent.putExtra("village",village);
        intent.putExtra("mandal",mandal);
        intent.putExtra("district",district);
        intent.putExtra("uname",uname);
        intent.putExtra("aadhar",aadhar);
        PS_Action.this.startActivity(intent);
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
                i = new Intent(this, PSAddEdit.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, PSAmountToDB.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, PSAmountDBToBen.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, PSGrounding.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
            case R.id.c5:
                i = new Intent(this, PSMasterReport.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
        }


    }
}