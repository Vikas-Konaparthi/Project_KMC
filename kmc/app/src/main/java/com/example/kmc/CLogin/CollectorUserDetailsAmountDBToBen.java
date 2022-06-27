package com.example.kmc.CLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kmc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class CollectorUserDetailsAmountDBToBen extends AppCompatActivity {

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
    public TextView getIndividualBankIFSC;
    public TextView getIndividualVendorBankAccount;
    public TextView getIndividualVendorName;
    public TextView getIndividualVendorBankIFSC;
    public TextView getIndividualVendorAgency;
    public TextView getIndividualVendorBankName;
    public TextView psRequestedAmount;
    public TextView getIndividualDBAmount;
    public TextView getIndividualsoRemarks;
    public TextView soQuotationAmount;
    public TextView getAmountApproved;
    private TextInputEditText collectorNewApprovalAmount;
    private TextInputEditText individualQAmount;
    public TextView getDbBankName;
    public TextView getDbAccNumber;
    public TextView getDbIFSC;
//    public TextView getIndividualRequestedAmount;
//    public TextView getSpApprovedAmount;
    Button release;
    Button sanction;
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
    ProgressBar pgsBar;
    String groundImage;
    Button groundImageButton;
    String collectorSanction;
    String soApprovalAmount;
    String collectorNewAppAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_user_details_amount_dbto_ben);
        pgsBar = (ProgressBar)findViewById(R.id.pBar);
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
        getIndividualVendorAgency=(TextView) findViewById(R.id.vendorAgency);
        getIndividualVendorBankAccount=(TextView) findViewById(R.id.vendorBankAccountNo);
        getIndividualVendorBankIFSC=(TextView) findViewById(R.id.vendorBankIFSC);
        getIndividualVendorName=(TextView) findViewById(R.id.vendorName);
        individualSPRemark = (TextView) findViewById(R.id.spRemark);
        getIndividualVendorBankName=(TextView) findViewById(R.id.vendorBankName);
        getIndividualsoRemarks=(TextView) findViewById(R.id.soRemarks);
        soQuotationAmount=(TextView) findViewById(R.id.soQuotationAmount);
        psRequestedAmount=(TextView) findViewById(R.id.psRequestedAmount);
        getIndividualDBAmount=(TextView) findViewById(R.id.dbAmount);
        getAmountApproved=(TextView) findViewById(R.id.approvedAmount);
        collectorNewApprovalAmount=(TextInputEditText) findViewById(R.id.collectorApprovalAmount);
        getDbBankName = (TextView) findViewById(R.id.DbBankName);
        getDbAccNumber = (TextView) findViewById(R.id.DbAccNumber);
        getDbIFSC = (TextView) findViewById(R.id.DbBankIFSC);


//        individualSPRemark=(TextView) findViewById(R.id.spRemark);
//        getIndividualRequestedAmount=(TextView) findViewById(R.id.requestedAmount);
//        getSpApprovedAmount=(TextView) findViewById(R.id.spApprovedAmount);

//            groundImageButton=(Button)findViewById(R.id.groundImage);
//            sanction=(Button)findViewById(R.id.sanction);
//            release=(Button)findViewById(R.id.release);
        approve=(Button)findViewById(R.id.approve);
        reject=(Button)findViewById(R.id.reject);
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
        individualSPRemark.setText("Special Officer Remark: "+getIntent().getStringExtra("uSPRemarks").toString());
        getIndividualVendorBankAccount.setText("Vendor Bank Account: "+getIntent().getStringExtra("uVendorBankAccountNo").toString());;
        getIndividualVendorName.setText("Vendor Name: "+getIntent().getStringExtra("uVendorName").toString());;
        getIndividualVendorBankIFSC.setText("Vendor Bank IFSC: "+getIntent().getStringExtra("uVendorIFSC").toString());;
        getIndividualVendorAgency.setText("Vendor Agency: "+getIntent().getStringExtra("uVendorAgency").toString());;
        getIndividualVendorBankName.setText("Vendor Bank Name: "+getIntent().getStringExtra("uVendorBankName").toString());;
        psRequestedAmount.setText("PS Requested Amount: "+getIntent().getStringExtra("psRequestedAmount").toString());;
        getIndividualDBAmount.setText("DB Account Amount: "+getIntent().getStringExtra("uDbAccount").toString());;
        getIndividualsoRemarks.setText("Section Officer Remark: "+getIntent().getStringExtra("usoRemarks").toString());;
        soQuotationAmount.setText("Section Officer Quotation: "+getIntent().getStringExtra("usoQuotationAmount").toString());;
        soApprovalAmount=getIntent().getStringExtra("usoQuotationAmount").toString();
        getAmountApproved.setText("Amount Approved: "+getIntent().getStringExtra("uApprovalAmount").toString());
        getDbBankName.setText("DB Bank Name: "+getIntent().getStringExtra("uDbBankName").toString());
        getDbAccNumber.setText("DB Account Number: "+getIntent().getStringExtra("uDbAccountNo").toString());
        getDbIFSC.setText("DB Account IFSC: "+getIntent().getStringExtra("uDbIFSC").toString());

        //            individualSORemark.setText("Section Officer Remark: "+getIntent().getStringExtra("uSORemarks").toString());
//            getIndividualVendorName.setText("Vendor Name: "+getIntent().getStringExtra("uVendorName").toString());
//            getIndividualVendorBankAccount.setText("Vendor Bank Account: "+getIntent().getStringExtra("uVendorBankAccount").toString());
//            getIndividualVendorBankIFSC.setText("Vendor Bank IFSC: "+getIntent().getStringExtra("uVendorIFSC").toString());
//            getIndividualGroundingStatus.setText("Grounding Status: "+getIntent().getStringExtra("uGroundingStatus").toString());
//            getIndividualApprovalAmount.setText("Approval Amount: "+getIntent().getStringExtra("uApprovalAmount").toString());
//            getIndividualDBAmount.setText("Dalita Bandhu Account Amount: "+getIntent().getStringExtra("uDbAccount").toString());
//            approvalAmount=getIntent().getStringExtra("uApprovalAmount").toString();
        aadharNumber=getIntent().getStringExtra("uAadharNumber").toString();
//        collectorSanction=getIntent().getStringExtra("uSPAmountApproved").toString();

//        String soApproved=getIntent().getStringExtra("uSOApproved").toString();
//            dbAccount=getIntent().getStringExtra("uDbAccount").toString();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            village= extras.getString("village");
        }
//        if(soApproved.equals("yes"))
//        {
//            approve.setEnabled(true);
//            reject.setEnabled(true);
//            individualQAmount.setEnabled(true);
//        }
//            individualQAmount.addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    enableReleaseIfReady();
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    qAmount=individualQAmount.getText().toString();
//                }
//            });
//            collectorApproved=getIntent().getStringExtra("uCollectorApproved").toString();
//            Toast.makeText(this, collectorApproved, Toast.LENGTH_SHORT).show();
//            if(collectorApproved.equals("yes"))
//            {
//                sanction.setEnabled(false);
//                approve.setEnabled(false);
//                reject.setEnabled(false);
//            }else if(collectorApproved.equals("no"))
//            {
//                sanction.setEnabled(false);
//                approve.setEnabled(false);
//                reject.setEnabled(false);
//            }

//
//            groundImage=getIntent().getStringExtra("uGroundingImage").toString();
//            if(!groundImage.equals(""))
//            {
//                groundImageButton.setEnabled(true);
//            }

    }
    //    public void groundingImage(View view){
//        Uri uri = Uri.parse(groundImage); // missing 'http://' will cause crashed
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
//    }
//    public void enableReleaseIfReady(){
//        boolean isReady = individualQAmount.getText().toString().length() > 3;
//        release.setEnabled(isReady);
//    }
//    public void release(View view) {
//        int amount1=Integer.parseInt(approvalAmount)+Integer.parseInt(qAmount);
//        int amount2=Integer.parseInt(dbAccount)-Integer.parseInt(qAmount);
//        if(amount2<=0)
//        {
//            Toast.makeText(this, "Insufficient amount in Dalit Bandhu Account.", Toast.LENGTH_SHORT).show();
//        }else{
//            pgsBar.setVisibility(View.VISIBLE);
//            approvalAmount(aadharNumber,String.valueOf(amount1),String.valueOf(amount2));
//            getIndividualApprovalAmount.setText("Approval Amount: "+amount1);
//            getIndividualDBAmount.setText("Dalita Bandhu Account Amount: "+amount2);
//
//        }
//    }
//    public void sanctionAmount(View view) {
//        sanctionAmount(aadharNumber);
//    }
    public void psdocument(View view) {
        String url=getIntent().getStringExtra("upsUpload").toString();
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    //    public void sodocument(View view) {
//        String url=getIntent().getStringExtra("usecOfficerUpload").toString();
//        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
//    }
    public void approve(View view) {

        String approved="yes";
        String soApproved="yes";
        if(collectorNewApprovalAmount.getText().toString().equals(""))
        {
            int updateDBAccount=Integer.parseInt(getIntent().getStringExtra("uDbAccount").toString())-Integer.parseInt(soApprovalAmount);
            String updateAmount=Integer.toString(updateDBAccount);
            int approvalAmount=Integer.parseInt(getIntent().getStringExtra("uApprovalAmount").toString())+Integer.parseInt(soApprovalAmount);
            status=approvalAmount+ " released to beneficiary account.";
            updateData(aadharNumber,approved,status,updateAmount,Integer.toString(approvalAmount),soApproved);
        }else{
            if(Integer.parseInt(collectorNewApprovalAmount.getText().toString())<=Integer.parseInt(getIntent().getStringExtra("uDbAccount").toString()))
            {
                collectorNewAppAmount=collectorNewApprovalAmount.getText().toString();
                int updateDBAccount=Integer.parseInt(getIntent().getStringExtra("uDbAccount").toString())-Integer.parseInt(collectorNewAppAmount);
                String updateAmount=Integer.toString(updateDBAccount);
                int approvalAmount=Integer.parseInt(getIntent().getStringExtra("uApprovalAmount").toString())+Integer.parseInt(collectorNewAppAmount);
                status=approvalAmount+ " released to beneficiary account.";
                updateData(aadharNumber,approved,status,updateAmount,Integer.toString(approvalAmount),soApproved);
            }else{
                Toast.makeText(this, "Insufficient amount in DB Account.", Toast.LENGTH_SHORT).show();
            }

        }



    }
    public void reject(View view) {
        String collectorSanctionAmount="";
        String approved="no";
        String soApproved="";
        status= "Rejected By Collector: "+getIntent().getStringExtra("uStatus").toString();
        int approvalAmount=Integer.parseInt(getIntent().getStringExtra("uApprovalAmount").toString());
        updateData(aadharNumber,approved,status,collectorSanctionAmount,Integer.toString(approvalAmount),soApproved);
    }
    private void updateData(String aadharNumber, String approved,String status,String collectorSanctionAmount,String approvalAmount,String soApproved) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("status", status);
        individualInfo.put("ctrApproved2", approved);
        individualInfo.put("dbAccount", collectorSanctionAmount);
        individualInfo.put("approvalAmount", approvalAmount);
        individualInfo.put("soApproved", soApproved);

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
                                    Toast.makeText(CollectorUserDetailsAmountDBToBen.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CollectorUserDetailsAmountDBToBen.this, CollectorAmountDBToBen.class);
                                    intent.putExtra("village",village);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CollectorUserDetailsAmountDBToBen.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    Toast.makeText(CollectorUserDetailsAmountDBToBen.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void sanctionAmount(String aadharNumber) {
//        Map<String, Object> individualInfo = new HashMap<String, Object>();
//        individualInfo.put("dbAccount", "1000000");
//        individualInfo.put("status", "Waiting for Section Officer Approval");
//        Toast.makeText(this, aadharNumber, Toast.LENGTH_SHORT).show();
//        db.collection("individuals").whereEqualTo("aadhar",aadharNumber)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful() && !task.getResult().isEmpty()){
//                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
//                    String documentID=documentSnapshot.getId();
//                    db.collection("individuals")
//                            .document(documentID)
//                            .update(individualInfo)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(CollectorUserDetails.this, "Status Approval: "+approved, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(CollectorUserDetails.this, CollectorListOfBen.class);
//                                    intent.putExtra("village",village);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(CollectorUserDetails.this, "Error occured", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }else{
//
//                    Toast.makeText(CollectorUserDetails.this, "Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//    private void approvalAmount(String aadharNumber,String amount1,String amount2) {
//        Map<String, Object> individualInfo = new HashMap<String, Object>();
//        individualInfo.put("dbAccount", "1000000");
//        individualInfo.put("approvalAmount", amount1);
//        individualInfo.put("dbAccount", amount2);
//        Toast.makeText(this, aadharNumber, Toast.LENGTH_SHORT).show();
//        db.collection("individuals").whereEqualTo("aadhar",aadharNumber)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful() && !task.getResult().isEmpty()){
//                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
//                    String documentID=documentSnapshot.getId();
//                    db.collection("individuals")
//                            .document(documentID)
//                            .update(individualInfo)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(CollectorUserDetails.this, "Approval Amount: "+amount1, Toast.LENGTH_SHORT).show();
//                                    pgsBar.setVisibility(View.GONE);
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(CollectorUserDetails.this, "Error occured", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }else{
//
//                    Toast.makeText(CollectorUserDetails.this, "Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}