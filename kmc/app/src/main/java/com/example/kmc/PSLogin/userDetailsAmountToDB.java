package com.example.kmc.PSLogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kmc.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class userDetailsAmountToDB extends AppCompatActivity {
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
    private TextInputEditText individualAmountRequired;
    public TextView getDbBankName;
    public TextView getDbAccNumber;
    public TextView getDbIFSC;


    //    private TextInputEditText individualVendorName;
//    private TextInputEditText individualVendorBankAccountNumber;
//    private TextInputEditText individualVendorBankIFSC;
//    private TextInputEditText individualVendorAgency;
//    private TextInputEditText individualVendorBankName;
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
//    String vendorName;
//    String vendorBankAccount;
//    String vendorBankIFSC;
//    String vendorAgency;
//    String vendorBankName;
    String amountRequired;

    private final int PICK_IMAGE_REQUEST = 22;
    String my_url="";
    Uri image_uri = null;
    ProgressBar pgsBar;

    //    Button uploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_amount_to_db);
        db=FirebaseFirestore.getInstance();

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
        getAmountApproved=(TextView) findViewById(R.id.approvedAmount);
        getDBAccountAmount=(TextView) findViewById(R.id.dbAmount);
        getDbBankName = (TextView) findViewById(R.id.DbBankName);
        getDbAccNumber = (TextView) findViewById(R.id.DbAccNumber);
        getDbIFSC = (TextView) findViewById(R.id.DbBankIFSC);
        individualAmountRequired=(TextInputEditText) findViewById(R.id.individualAmountRequired);
//        individualVendorName=(TextInputEditText) findViewById(R.id.vendorName);
//        individualVendorBankAccountNumber=(TextInputEditText) findViewById(R.id.vendorBankAccountNo);
//        individualVendorBankIFSC=(TextInputEditText) findViewById(R.id.vendorBankIFSC);
//        individualVendorAgency=(TextInputEditText) findViewById(R.id.vendorAgency);
//        individualVendorBankName=(TextInputEditText) findViewById(R.id.vendorBankName);
//        uploadImage=(Button) findViewById(R.id.uploadImage);
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
        getAmountApproved.setText("Amount Approved: "+getIntent().getStringExtra("uApprovalAmount").toString());
        getDBAccountAmount.setText("DB Account Amount: "+getIntent().getStringExtra("uDBAccount").toString());
        getDbBankName.setText("DB Bank Name: "+getIntent().getStringExtra("uDbBankName").toString());
        getDbAccNumber.setText("DB Account Number: "+getIntent().getStringExtra("uDbAccountNo").toString());
        getDbIFSC.setText("DB Account IFSC: "+getIntent().getStringExtra("uDbIFSC").toString());

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

    public void uploadGrounding(View view) {
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
        amountRequired=individualAmountRequired.getText().toString();
//        vendorBankAccount=individualVendorBankAccountNumber.getText().toString();
//        vendorName=individualVendorName.getText().toString();
//        vendorBankIFSC=individualVendorBankIFSC.getText().toString();
//        vendorAgency=individualVendorAgency.getText().toString();
//        vendorBankName=individualVendorBankName.getText().toString();
//        if(collectorApproved.equals("yes"))
//        {
//            uploadImage.setEnabled(true);
//        }
        if((Integer.parseInt(getIntent().getStringExtra("uApprovalAmount").toString())+Integer.parseInt(amountRequired.trim())+Integer.parseInt(getIntent().getStringExtra("uDBAccount").toString()))<=1000000)
            updateData(aadharNumber,amountRequired);
        else
            Toast.makeText(this, "Amount Limit Exceeded", Toast.LENGTH_SHORT).show();
//        updateData(aadharNumber,vendorAgency,vendorName,vendorBankName,vendorBankAccount,vendorBankIFSC);
    }

    public void updateData(String aadharNumber,String amountRequired){
        if (amountRequired.length() != 0) {
            Map<String, Object> individualInfo = new HashMap<String, Object>();
            individualInfo.put("individualAmountRequired", amountRequired.trim());
            individualInfo.put("status", "Waiting for "+amountRequired.trim()+ " approval to DB Account.");
            individualInfo.put("psApproved","yes");
            individualInfo.put("psApproved3","");
            individualInfo.put("spApproved2","");

//        individualInfo.put("vendorName", vendorName.trim());
//        individualInfo.put("vendorAccountNo", vendorBankAccount.trim());
//        individualInfo.put("vendorIFSC", vendorBankIFSC.trim());
//        individualInfo.put("vendorAgency", vendorAgency.trim());
//        individualInfo.put("vendorBankName", vendorBankName.trim());
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

                                        Toast.makeText(userDetailsAmountToDB.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(userDetailsAmountToDB.this, PSAmountToDB.class);
                                        i.putExtra("village", village.trim());
                                        i.putExtra("mandal", mandal.trim());
                                        i.putExtra("district", district.trim());
                                        startActivity(i);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(userDetailsAmountToDB.this, "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(userDetailsAmountToDB.this, "Failed", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(userDetailsAmountToDB.this, image_uri.toString(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(userDetailsAmountToDB.this,"File Uploaded Successfully",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(my_url));
//                        startActivity(i);
                    }else{
                        Toast.makeText(userDetailsAmountToDB.this,"Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}

