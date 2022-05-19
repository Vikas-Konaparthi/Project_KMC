package com.example.kmc.CLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kmc.R;
import com.example.kmc.SPLogin.SPUserDetails;
import com.example.kmc.SPLogin.SPZone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CollectorUserDetails extends AppCompatActivity {
    public TextView individualName;
    public TextView individualFatherName;
    public TextView individualAge;
    public TextView individualHouseNo;
    public TextView individualAadhar;
    public TextView individualPhno;
    public TextView individualVillage;
    public TextView individualMandal;
    public TextView individualDistrict;
    public TextView individualPreferredUnit;
    public TextView individualBankName;
    public TextView individualBankAccNo;
    public TextView individualPSUpload;
    public TextView individualSPRemark;
    public TextView individualSORemark;
    private TextInputEditText individualSPRemarks;
    private TextInputEditText individualQAmount;
    Button release;

    Button approve;
    Button reject;
    String approved;
    FirebaseFirestore db;
    String indivName;
    String fatherName;
    String age;
    String houseNumber;
    String aadharNumber;
    String mobileNumber;
    String preferredunit;
    String bankName;
    String bankACCNumber;
    String spRemarks;
    String soRemarks;
    String releaseAmount;
    String collectorApproved;
    String status;
    String qAmount;
    String approvalAmount;
    String dbAccount;
    String village;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_user_details);
        db= FirebaseFirestore.getInstance();
        individualName  = (TextView) findViewById(R.id.IndividualName);
        individualFatherName=(TextView) findViewById(R.id.FatherName);
        individualAge=(TextView) findViewById(R.id.Age);
        individualHouseNo=(TextView) findViewById(R.id.HouseNumber);
        individualVillage=(TextView) findViewById(R.id.village);
        individualMandal=(TextView) findViewById(R.id.mandal);
        individualDistrict=(TextView) findViewById(R.id.district);
        individualAadhar=(TextView) findViewById(R.id.AadharNumber);
        individualPhno=(TextView) findViewById(R.id.MobileNumber);
        individualPreferredUnit=(TextView) findViewById(R.id.Preferredunit);
        individualBankName=(TextView) findViewById(R.id.BankName);
        individualBankAccNo=(TextView) findViewById(R.id.BankACCNumber);
        individualSPRemark=(TextView) findViewById(R.id.spRemark);
        individualSORemark=(TextView) findViewById(R.id.soRemark);
        individualQAmount=(TextInputEditText) findViewById(R.id.qAmount);

        release=(Button)findViewById(R.id.release);
        approve=(Button)findViewById(R.id.approve);
        reject=(Button)findViewById(R.id.reject);
        individualName.setText("Name: "+getIntent().getStringExtra("uname").toString());
        individualFatherName.setText("Father Name: "+getIntent().getStringExtra("ufname").toString());
        individualAge.setText("Age : "+getIntent().getStringExtra("uAge").toString());
        individualHouseNo.setText("House Number: "+getIntent().getStringExtra("uHnumber").toString());
        individualVillage.setText("Village: "+getIntent().getStringExtra("uVillage").toString());
        individualMandal.setText("Mandal: "+getIntent().getStringExtra("uMandal").toString());
        individualDistrict.setText("District: "+getIntent().getStringExtra("uDistrict").toString());
        individualAadhar.setText("Aadhar Number: "+getIntent().getStringExtra("uAadharNumber").toString());
        individualPhno.setText("Mobile Number: "+getIntent().getStringExtra("uMobileNo").toString());
        individualPreferredUnit.setText("Preferred Unit: "+getIntent().getStringExtra("uPreferredUnit").toString());
        individualBankName.setText("Bank Name: "+getIntent().getStringExtra("uBankName").toString());
        individualBankAccNo.setText("Bank Account Number: "+getIntent().getStringExtra("uBankAccNumber").toString());
        individualSPRemark.setText("Special Officer Remark: "+getIntent().getStringExtra("uSPRemarks").toString());
        individualSORemark.setText("Section Officer Remark: "+getIntent().getStringExtra("uSORemarks").toString());
        approvalAmount=getIntent().getStringExtra("uCollectorApprovalAmount").toString();
        aadharNumber=getIntent().getStringExtra("uAadharNumber").toString();
        String soApproved=getIntent().getStringExtra("uSOApproved").toString();
        dbAccount=getIntent().getStringExtra("uDBAccount").toString();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            village= extras.getString("village");
        }
        if(soApproved.equals("yes"))
        {
            approve.setEnabled(true);
            reject.setEnabled(true);
            individualQAmount.setEnabled(true);
        }
        individualQAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                enableReleaseIfReady();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                qAmount=individualQAmount.getText().toString();
            }
        });
        collectorApproved=getIntent().getStringExtra("uCollectorApproved").toString();
        Toast.makeText(this, collectorApproved, Toast.LENGTH_SHORT).show();
        if(collectorApproved.equals("yes"))
        {

            approve.setEnabled(false);
            reject.setEnabled(false);
        }



    }
    public void enableReleaseIfReady(){
        boolean isReady = individualQAmount.getText().toString().length() > 3;
        release.setEnabled(isReady);
    }
    public void release(View view) {
        int amount1=Integer.parseInt(approvalAmount)+Integer.parseInt(qAmount);
        int amount2=Integer.parseInt(dbAccount)-amount1;

        approvalAmount(aadharNumber,String.valueOf(amount1),String.valueOf(amount2));
    }
    public void sanctionAmount(View view) {
        sanctionAmount(aadharNumber);
    }
    public void psdocument(View view) {
        String url=getIntent().getStringExtra("upsUpload").toString();
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void sodocument(View view) {
        String url=getIntent().getStringExtra("usecOfficerUpload").toString();
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void approve(View view) {
        String approved="yes";
        status="Approved";
        updateData(aadharNumber,approved,status);
    }


    public void reject(View view) {
        String approved="no";
        status="Rejected";
        updateData(aadharNumber,approved,status);
    }
    private void updateData(String aadharNumber, String approved,String status) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("dbAccount", "1000000");
        individualInfo.put("status", status);
        individualInfo.put("ctrApproved", approved);
        Toast.makeText(this, aadharNumber, Toast.LENGTH_SHORT).show();
        db.collection("individuals").whereEqualTo("aadhar",aadharNumber)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentID=documentSnapshot.getId();
                    db.collection("individuals")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CollectorUserDetails.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CollectorUserDetails.this, CollectorZone.class);
                                    intent.putExtra("village",village);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CollectorUserDetails.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    Toast.makeText(CollectorUserDetails.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sanctionAmount(String aadharNumber) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("dbAccount", "1000000");
        individualInfo.put("status", "Waiting for Section Officer Approval");
        Toast.makeText(this, aadharNumber, Toast.LENGTH_SHORT).show();
        db.collection("individuals").whereEqualTo("aadhar",aadharNumber)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentID=documentSnapshot.getId();
                    db.collection("individuals")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CollectorUserDetails.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CollectorUserDetails.this, CollectorZone.class);
                                    intent.putExtra("village",village);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CollectorUserDetails.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    Toast.makeText(CollectorUserDetails.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void approvalAmount(String aadharNumber,String amount1,String amount2) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("dbAccount", "1000000");
        individualInfo.put("approvalAmount", amount1);
        individualInfo.put("dbAccount", amount2);
        Toast.makeText(this, aadharNumber, Toast.LENGTH_SHORT).show();
        db.collection("individuals").whereEqualTo("aadhar",aadharNumber)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentID=documentSnapshot.getId();
                    db.collection("individuals")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CollectorUserDetails.this, "Approval Amount: "+amount1, Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CollectorUserDetails.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    Toast.makeText(CollectorUserDetails.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}