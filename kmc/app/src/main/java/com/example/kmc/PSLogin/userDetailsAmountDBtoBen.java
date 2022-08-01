package com.example.kmc.PSLogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kmc.R;
import com.example.kmc.Vendor;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userDetailsAmountDBtoBen extends AppCompatActivity {

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
    public TextView getAmountApproved;
    public TextView getDBAccountAmount;
    public TextView individualRequestedAmount;
    public TextView individualApprovedAmount;
    public TextView individualDBAccount;
    public TextView getDbBankName;
    public TextView getDbAccNumber;
    public TextView getDbIFSC;
    private TextInputEditText individualAmountRequired;
    private TextInputEditText individualVendorName;
    private TextInputEditText individualVendorBankAccountNumber;
    private TextInputEditText individualVendorBankIFSC;
    private TextInputEditText individualVendorBankName;
    private Spinner individualVendorAgency;
    private TextInputEditText vendorAgency;
    private TextInputEditText approvalAmountToBeneficiary;
    FirebaseFirestore db;
    String indivName;
    String fatherName;
    String age;
    String houseNumber;
    String village;
    String mandal;
    String district;
    String aadharNumber;
    String mobileNumber;
    String preferredunit;
    String bankName;
    String bankACCNumber;
    String collectorApproved="";
    String bankIFSC;
    //    String groundingStatus="";
    String option_selected;
    String vendorName;
    String vendorBankAccount;
    String vendorBankIFSC;
    String agencyName;
    String vendorBankName;
    String amountRequired;
    String spRemarks;
    String requestedAmount;
    String approvedAmount;
    String approvalAmountToBen;
    String indDBAccount;

    private final int PICK_IMAGE_REQUEST = 22;
    String my_url="";
    Uri image_uri = null;
    ProgressBar pgsBar;
    Vendor obj;
    List<DocumentSnapshot> list;

    Button uploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_amount_dbto_ben);
        vendorAgency=(TextInputEditText) findViewById(R.id.others_vendorAgency);
        db=FirebaseFirestore.getInstance();

        Spinner spinnerVendorAgency = (Spinner) findViewById(R.id.spinner_vendorAgency);
        List<String> agencies = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                agencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVendorAgency.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();

        db.collection("vendorAgency").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {

                            obj = d.toObject(Vendor.class);

                            agencies.add(obj.getAgencyName());
                        }
                        adapter.notifyDataSetChanged();
                        option_selected = adapter.getItem(0);
                    }

                });

        spinnerVendorAgency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {

                spinnerVendorAgency.setSelection(position);
                option_selected = spinnerVendorAgency.getSelectedItem().toString();
                if(option_selected.equalsIgnoreCase("others")){
                    vendorAgency.setText("");
                    individualVendorName.setText("");
                    individualVendorBankAccountNumber.setText("");
                    individualVendorBankIFSC.setText("");
                    individualVendorBankName.setText("");
                }
                else{
                    for(DocumentSnapshot d:list) {
                        obj = d.toObject(Vendor.class);
                        if (obj.getAgencyName().equalsIgnoreCase(option_selected)) {
                            agencyName = option_selected;
                            vendorAgency.setText(option_selected);
                            individualVendorName.setText(obj.getVendorName());
                            individualVendorBankAccountNumber.setText(obj.getVendorBankAcc());
                            individualVendorBankIFSC.setText(obj.getVendorBankIFSC());
                            individualVendorBankName.setText(obj.getVendorBankName());
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        pgsBar = (ProgressBar)findViewById(R.id.pBar);
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
        individualPSUpload=(TextView) findViewById(R.id.psUpload);
        individualDBAccount=(TextView) findViewById(R.id.dbAccount);
        getAmountApproved=(TextView) findViewById(R.id.approvedAmount);
        getDbBankName = (TextView) findViewById(R.id.DbBankName);
        getDbAccNumber = (TextView) findViewById(R.id.DbAccNumber);
        getDbIFSC = (TextView) findViewById(R.id.DbBankIFSC);

        individualAmountRequired=(TextInputEditText) findViewById(R.id.individualAmountRequired);
        individualVendorName=(TextInputEditText) findViewById(R.id.vendorName);
        individualVendorBankAccountNumber=(TextInputEditText) findViewById(R.id.vendorBankAccountNo);
        individualVendorBankIFSC=(TextInputEditText) findViewById(R.id.vendorBankIFSC);
        individualVendorAgency= (Spinner) findViewById(R.id.spinner_vendorAgency);
        individualVendorBankName=(TextInputEditText) findViewById(R.id.vendorBankName);
        approvalAmountToBeneficiary=(TextInputEditText) findViewById(R.id.approvalAmountToBeneficiary);
        uploadImage=(Button) findViewById(R.id.uploadImage);
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
        individualDBAccount.setText("DB Account: "+getIntent().getStringExtra("uDBAccount").toString());
        getAmountApproved.setText("Amount Approved: "+getIntent().getStringExtra("uApprovalAmount").toString());
        getDbBankName.setText("DB Bank Name: "+getIntent().getStringExtra("uDbBankName").toString());
        getDbIFSC.setText("DB Account IFSC: "+getIntent().getStringExtra("uDbIFSC").toString());
        getDbAccNumber.setText("DB Account Number: "+getIntent().getStringExtra("uDbAccountNo").toString());
        //        individualVendorName.getEditText().setText(getIntent().getStringExtra("uVendorName").toString());
//        individualVendorBankAccountNumber.getEditText().setText(getIntent().getStringExtra("uVendorBankAccount").toString());
//        individualVendorBankIFSC.getEditText().setText(getIntent().getStringExtra("uVendorIFSC").toString());
        village=getIntent().getStringExtra("uVillage").toString();
        mandal=getIntent().getStringExtra("uMandal").toString();
        district=getIntent().getStringExtra("uDistrict").toString();
        collectorApproved=getIntent().getStringExtra("uCollectorApproved").toString();
//        if(collectorApproved.equals("yes"))
//        {
//            uploadImage.setEnabled(true);
//            groundingStatus="Successfully Grounded";
//        }




    }

    public void uploadQuoteImg(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }
    public void submitButton(View view) {
        aadharNumber=getIntent().getStringExtra("uAadharNumber").toString();
//        amountRequired=individualAmountRequired.getText().toString();
        vendorBankAccount=individualVendorBankAccountNumber.getText().toString();
        vendorName=individualVendorName.getText().toString();
        vendorBankIFSC=individualVendorBankIFSC.getText().toString();
        agencyName=vendorAgency.getText().toString();
        vendorBankName=individualVendorBankName.getText().toString();
        approvalAmountToBen=approvalAmountToBeneficiary.getText().toString();
        indDBAccount = getIntent().getStringExtra("uDBAccount").toString();
//        if(collectorApproved.equals("yes"))
//        {
//            uploadImage.setEnabled(true);
//        }
//        updateData(aadharNumber,vendorAgency,vendorName,vendorBankName,vendorBankAccount,vendorBankIFSC,my_url,approvalAmountToBen);
        if((Integer.parseInt(approvalAmountToBen)<=Integer.parseInt(indDBAccount))){
//            int Amount = Integer.parseInt(indDBAccount)-Integer.parseInt(approvalAmountToBen);
//            indDBAccount = Integer.toString(Amount);
            if(!option_selected.equalsIgnoreCase("others"))
                updateData(aadharNumber, agencyName, vendorName, vendorBankName, vendorBankAccount, vendorBankIFSC, my_url, approvalAmountToBen, indDBAccount);
            else
                updateData2(aadharNumber, agencyName, vendorName, vendorBankName, vendorBankAccount, vendorBankIFSC, my_url, approvalAmountToBen, indDBAccount);
            individualDBAccount.setText("DB Account: " + indDBAccount.trim());
        }
        else{
            System.out.println("Hello");
            Toast.makeText(this, "Insufficient amount in DB Account", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData(String aadharNumber,String vendorAgency,String vendorName,String vendorBankName,String vendorBankAccount,String vendorBankIFSC,String img_url,String appAmountToBen,String indDBAccount){
        if (vendorAgency.length() != 0 && vendorName.length() != 0 && vendorBankName.length() != 0 && vendorBankAccount.length() != 0 && vendorBankIFSC.length() != 0 && img_url.length()!=0 && appAmountToBen.length()!=0) {


            Map<String, Object> individualInfo = new HashMap<String, Object>();
//            individualInfo.put("individualAmountRequired", amountRequired.trim());
            individualInfo.put("vendorName", vendorName.trim());
            individualInfo.put("vendorAccountNo", vendorBankAccount.trim());
            individualInfo.put("vendorIFSC", vendorBankIFSC.trim());
            individualInfo.put("vendorAgency", vendorAgency.trim());
            individualInfo.put("vendorBankName", vendorBankName.trim());
            individualInfo.put("status", "Requesting for "+appAmountToBen.trim()+" to Beneficiary from DB Account.");

            individualInfo.put("psRequestedAmountToBeneficiary", appAmountToBen.trim());
            individualInfo.put("quotationImage", img_url.trim());
            individualInfo.put("psApproved2","yes");
            individualInfo.put("soApproved","yes");
            individualInfo.put("spApproved3","NA");
            individualInfo.put("so_quotation_amount", appAmountToBen.trim());
//            individualInfo.put("approvalAmountToBen",indDBAccount.trim());
//        individualInfo.put("groundingStatus", groundingStatus);


            db.collection("individuals").whereEqualTo("aadhar", aadharNumber)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        String documentID = documentSnapshot.getId();
                        db.collection("individuals")
                                .document(documentID)
                                .update(individualInfo)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(userDetailsAmountDBtoBen.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(userDetailsAmountDBtoBen.this, PSAmountDBToBen.class);
                                        i.putExtra("village", village.trim());
                                        i.putExtra("mandal", mandal.trim());
                                        i.putExtra("district", district.trim());
                                        startActivity(i);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(userDetailsAmountDBtoBen.this, "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(userDetailsAmountDBtoBen.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateData2(String aadharNumber,String vendorAgency,String vendorName,String vendorBankName,String vendorBankAccount,String vendorBankIFSC,String img_url,String appAmountToBen,String indDBAccount){
        if (vendorAgency.length() != 0 && vendorName.length() != 0 && vendorBankName.length() != 0 && vendorBankAccount.length() != 0 && vendorBankIFSC.length() != 0 && img_url.length()!=0 && appAmountToBen.length()!=0) {


            Map<String, Object> individualInfo = new HashMap<String, Object>();
//            individualInfo.put("individualAmountRequired", amountRequired.trim());
            individualInfo.put("vendorName", vendorName.trim());
            individualInfo.put("vendorAccountNo", vendorBankAccount.trim());
            individualInfo.put("vendorIFSC", vendorBankIFSC.trim());
            individualInfo.put("vendorAgency", vendorAgency.trim());
            individualInfo.put("vendorBankName", vendorBankName.trim());
            individualInfo.put("status", "Requesting for "+appAmountToBen.trim()+" to Beneficiary from DB Account. New Vendor Details were added.");

            individualInfo.put("psRequestedAmountToBeneficiary", appAmountToBen.trim());
            individualInfo.put("quotationImage", img_url.trim());
            individualInfo.put("psApproved2","yes");
            individualInfo.put("spApproved3","yes");
//            individualInfo.put("approvalAmountToBen",indDBAccount.trim());
//        individualInfo.put("groundingStatus", groundingStatus);


            db.collection("individuals").whereEqualTo("aadhar", aadharNumber)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                String documentID = documentSnapshot.getId();
                                db.collection("individuals")
                                        .document(documentID)
                                        .update(individualInfo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Toast.makeText(userDetailsAmountDBtoBen.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(userDetailsAmountDBtoBen.this, PSAmountDBToBen.class);
                                                i.putExtra("village", village.trim());
                                                i.putExtra("mandal", mandal.trim());
                                                i.putExtra("district", district.trim());
                                                startActivity(i);
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(userDetailsAmountDBtoBen.this, "Error occured", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else {
                                Toast.makeText(userDetailsAmountDBtoBen.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        pgsBar.setVisibility(View.VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            image_uri = data.getData();
            final String timestamp = ""+System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePUSHID = timestamp;
            Toast.makeText(userDetailsAmountDBtoBen.this, image_uri.toString(),Toast.LENGTH_SHORT).show();
            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePUSHID+"."+"pdf");
            filepath.putFile(image_uri).continueWithTask(new Continuation(){
                @Override
                public Object then(@NonNull Task task) throws  Exception{
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>(){
                @Override
                public void onComplete(@NonNull Task<Uri> task){
                    if(task.isSuccessful()){
                        Uri uri = task.getResult();
                        my_url = uri.toString();
                        pgsBar.setVisibility(View.GONE);
                        Toast.makeText(userDetailsAmountDBtoBen.this,"File Uploaded Successfully",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(my_url));
//                        startActivity(i);
                    }else{
                        Toast.makeText(userDetailsAmountDBtoBen.this,"Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}