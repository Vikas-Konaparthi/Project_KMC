package com.example.kmc.SPLogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kmc.R;
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

public class SPUserDetailsAmountToDB extends AppCompatActivity {

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
    public TextView getIndividualBankIFSC;
    public TextView getIndividualRequestedAmount;
    private TextInputEditText individualSPRemarks;
    private TextInputEditText spAmountApproved;
    public TextView getIndividualDBAmount;
    public TextView getIndividualApprovalAmount;
    public TextView getAmountApproved;
    public TextView getDBAccountAmount;
    public TextView getDbBankName;
    public TextView getDbAccNumber;
    public TextView getDbIFSC;

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
    String collectorApproved;
    String status;
    String village;
    String mandal;
    String district;
    String village1;
    String village2;
    String groundImage;
    Button groundImageButton;
    String spApprovedAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spuser_details_amount_release_to_db);
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
        getIndividualBankIFSC=(TextView) findViewById(R.id.BankIFSC);
        getIndividualRequestedAmount=(TextView) findViewById(R.id.requestedAmount);
        individualPSUpload=(TextView) findViewById(R.id.psUpload);
        individualSPRemarks=(TextInputEditText) findViewById(R.id.remarks);
        spAmountApproved=(TextInputEditText) findViewById(R.id.spAmountApproved);
        getDbBankName = (TextView) findViewById(R.id.DbBankName);
        getDbAccNumber = (TextView) findViewById(R.id.DbAccNumber);
        getDbIFSC = (TextView) findViewById(R.id.DbBankIFSC);

        getAmountApproved=(TextView) findViewById(R.id.approvedAmount);
        getDBAccountAmount=(TextView) findViewById(R.id.dbAmount);
//        getIndividualDBAmount=(TextView) findViewById(R.id.dbAmount);
        getIndividualApprovalAmount=(TextView) findViewById(R.id.approvalAmount);
        approve=(Button)findViewById(R.id.approve);
        reject=(Button)findViewById(R.id.reject);
//        groundImageButton=(Button)findViewById(R.id.groundImage);

        individualName.setText("Name: "+getIntent().getStringExtra("uname").toString());
        individualFatherName.setText("Father Name: "+getIntent().getStringExtra("ufname").toString());
        individualAge.setText("Age: "+getIntent().getStringExtra("uAge").toString());
        individualHouseNo.setText("House Number: "+getIntent().getStringExtra("uHnumber").toString());
        individualVillage.setText("Village: "+getIntent().getStringExtra("uVillage").toString());
        individualMandal.setText("Mandal: "+getIntent().getStringExtra("uMandal").toString());
        individualDistrict.setText("District: "+getIntent().getStringExtra("uDistrict").toString());
        individualAadhar.setText("Aadhar Number: "+getIntent().getStringExtra("uAadharNumber").toString());
        individualPhno.setText("Mobile Number: "+getIntent().getStringExtra("uMobileNo").toString());
        individualPreferredUnit.setText("Preferred Unit: "+getIntent().getStringExtra("uPreferredUnit").toString());
        individualBankName.setText("Bank Name: "+getIntent().getStringExtra("uBankName").toString());
        individualBankAccNo.setText("Bank Account Number: "+getIntent().getStringExtra("uBankAccNumber").toString());
        getIndividualBankIFSC.setText("Bank IFSC: "+getIntent().getStringExtra("uBankIFSC").toString());
        getIndividualRequestedAmount.setText("Requested Amount: "+getIntent().getStringExtra("uRequestedAmount").toString());
        getAmountApproved.setText("Amount Approved: "+getIntent().getStringExtra("uApprovalAmount").toString());
        getDBAccountAmount.setText("DB Account Amount: "+getIntent().getStringExtra("uDbAccount").toString());
        getDbBankName.setText("DB Bank Name: "+getIntent().getStringExtra("uDbBankName").toString());
        getDbAccNumber.setText("DB Account Number: "+getIntent().getStringExtra("uDbAccountNo").toString());
        getDbIFSC.setText("DB Account IFSC: "+getIntent().getStringExtra("uDbIFSC").toString());
        spAmountApproved.setText(getIntent().getStringExtra("uRequestedAmount").toString());
        //        getIndividualDBAmount.setText("Dalita Bandhu Account Amount: "+getIntent().getStringExtra("uDbAccount").toString());

        aadharNumber=getIntent().getStringExtra("uAadharNumber").toString();
        village=getIntent().getStringExtra("uVillage").toString();
        mandal= getIntent().getStringExtra("uMandal").toString();
        district=getIntent().getStringExtra("uDistrict").toString();
        village1=getIntent().getStringExtra("village1").toString();
        village2=getIntent().getStringExtra("village2").toString();
        spAmountApproved.setText(getIntent().getStringExtra("uRequestedAmount").toString());
        spApprovedAmount= spAmountApproved.getText().toString();
        individualSPRemarks.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                enableSubmitIfReady();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                spRemarks= individualSPRemarks.getText().toString();
            }
        });
        spAmountApproved.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                enableSubmitIfReady();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                spApprovedAmount= spAmountApproved.getText().toString();
            }
        });
        collectorApproved=getIntent().getStringExtra("uCollectorApproved").toString();
//        if(collectorApproved.equals("yes"))
//        {
//            approve.setEnabled(false);
//            reject.setEnabled(false);
//            individualSPRemarks.setEnabled(false);
//        }else if(collectorApproved.equals("no"))
//        {
//            approve.setEnabled(false);
//            reject.setEnabled(false);
//            individualSPRemarks.setEnabled(false);
//        }


//        groundImage=getIntent().getStringExtra("uGroundingImage").toString();
//        if(!groundImage.equals(""))
//        {
//            groundImageButton.setEnabled(true);
//        }

    }
//    public void groundingImage(View view){
//        Uri uri = Uri.parse(groundImage); // missing 'http://' will cause crashed
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
//    }
    public void enableSubmitIfReady(){
        boolean isReady = individualSPRemarks.getText().toString().length() > 3;
        boolean isReady2 = spAmountApproved.getText().toString().length() > 3;
        approve.setEnabled(isReady&&isReady2);
        reject.setEnabled(isReady&&isReady2);
    }

    public void document(View view) {
        String url=getIntent().getStringExtra("psUpload").toString();
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
          }
    public void approve(View view) {
        String approved="yes";
        status=spApprovedAmount.trim()+" approved by Special Officer to DB Account ";
        String psApproved="yes";
        if((Integer.parseInt(spApprovedAmount)+(Integer.parseInt(getIntent().getStringExtra("uApprovalAmount")))+(Integer.parseInt(getIntent().getStringExtra("uDbAccount"))))<=1000000)
        {
            updateData(aadharNumber,approved,status,psApproved);
        }else{
            Toast.makeText(this, "Amount limit exceed.", Toast.LENGTH_SHORT).show();
        }
    }


    public void reject(View view) {
        String approved="no";
        String psApproved="NA";
        status= "Rejected By SP: "+getIntent().getStringExtra("uStatus").toString();
        updateData(aadharNumber,approved,status,psApproved);
    }
    private void updateData(String aadharNumber, String approved,String status,String psApproved) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("spApproved2", approved.trim());
        individualInfo.put("sp_remarks", spRemarks.trim());
        individualInfo.put("ctrApproved", "NA");
        individualInfo.put("spAmountApproved", spApprovedAmount.trim());
        individualInfo.put("psApproved", psApproved);
        individualInfo.put("status", status);
        individualInfo.put("ctrNote1", "NA");
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
                                    Toast.makeText(SPUserDetailsAmountToDB.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SPUserDetailsAmountToDB.this, SPAmountToDB.class);
                                    intent.putExtra("village1",village1);
                                    intent.putExtra("village2",village2);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SPUserDetailsAmountToDB.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    Toast.makeText(SPUserDetailsAmountToDB.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}