package com.example.kmc.PSLogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kmc.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class userDetails extends AppCompatActivity {
    public TextInputLayout individualName;
    public TextInputLayout individualFatherName;
    public TextInputLayout individualAge;
    public TextInputLayout individualHouseNo;
    public TextInputLayout individualVillage;
    public TextInputLayout individualMandal;
    public TextInputLayout individualDistrict;
    public TextInputLayout individualAadhar;
    public TextInputLayout individualPhno;
    public TextInputLayout individualPreferredUnit;
    public TextInputLayout individualBankName;
    public TextInputLayout individualBankAccNo;
    public TextInputLayout individualBankIFSC;
    private TextInputLayout individualVendorName;
    private TextInputLayout individualVendorBankAccountNumber;
    private TextInputLayout individualVendorBankIFSC;
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
    private final int PICK_IMAGE_REQUEST = 22;
    String my_url="";
    Uri image_uri = null;
    ProgressBar pgsBar;

//    Button uploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        db=FirebaseFirestore.getInstance();
        pgsBar = (ProgressBar)findViewById(R.id.pBar);
        individualName  = (TextInputLayout) findViewById(R.id.IndividualName);
        individualFatherName=(TextInputLayout) findViewById(R.id.FatherName);
        individualAge=(TextInputLayout) findViewById(R.id.Age);
        individualHouseNo=(TextInputLayout) findViewById(R.id.HouseNumber);
        individualVillage=(TextInputLayout) findViewById(R.id.village);
        individualMandal=(TextInputLayout) findViewById(R.id.mandal);
        individualDistrict=(TextInputLayout) findViewById(R.id.district);
        individualAadhar=(TextInputLayout) findViewById(R.id.AadharNumber);
        individualPhno=(TextInputLayout) findViewById(R.id.MobileNumber);
        individualPreferredUnit=(TextInputLayout) findViewById(R.id.Preferredunit);
        individualBankName=(TextInputLayout) findViewById(R.id.BankName);
        individualBankAccNo=(TextInputLayout) findViewById(R.id.BankACCNumber);
        individualBankIFSC=(TextInputLayout) findViewById(R.id.BankIFSC);
//        individualVendorName=(TextInputLayout) findViewById(R.id.vendorName);
//        individualVendorBankAccountNumber=(TextInputLayout) findViewById(R.id.vendorBankAccountNo);
//        individualVendorBankIFSC=(TextInputLayout) findViewById(R.id.vendorBankIFSC);
//        uploadImage=(Button) findViewById(R.id.uploadImage);
        individualName.getEditText().setText(getIntent().getStringExtra("uname").toString());
        individualFatherName.getEditText().setText(getIntent().getStringExtra("ufname").toString());
        individualAge.getEditText().setText(getIntent().getStringExtra("uAge").toString());
        individualHouseNo.getEditText().setText(getIntent().getStringExtra("uHnumber").toString());
        individualVillage.getEditText().setText(getIntent().getStringExtra("uVillage").toString());
        individualMandal.getEditText().setText(getIntent().getStringExtra("uMandal").toString());
        individualDistrict.getEditText().setText(getIntent().getStringExtra("uDistrict").toString());
        individualAadhar.getEditText().setText(getIntent().getStringExtra("uAadharNumber").toString());
        individualPhno.getEditText().setText(getIntent().getStringExtra("uMobileNo").toString());
        individualPreferredUnit.getEditText().setText(getIntent().getStringExtra("uPreferredUnit").toString());
        individualBankName.getEditText().setText(getIntent().getStringExtra("uBankName").toString());
        individualBankAccNo.getEditText().setText(getIntent().getStringExtra("uBankAccNumber").toString());
        individualBankIFSC.getEditText().setText(getIntent().getStringExtra("uBankIFSC").toString());
//        individualVendorName.getEditText().setText(getIntent().getStringExtra("uVendorName").toString());
//        individualVendorBankAccountNumber.getEditText().setText(getIntent().getStringExtra("uVendorBankAccount").toString());
//        individualVendorBankIFSC.getEditText().setText(getIntent().getStringExtra("uVendorIFSC").toString());

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
        indivName = individualName.getEditText().getText().toString();
        fatherName = individualFatherName.getEditText().getText().toString();
        age = individualAge.getEditText().getText().toString();
        houseNumber = individualHouseNo.getEditText().getText().toString();
        aadharNumber = individualAadhar.getEditText().getText().toString();
        mobileNumber = individualPhno.getEditText().getText().toString();
        preferredunit = individualPreferredUnit.getEditText().getText().toString();
        bankName = individualBankName.getEditText().getText().toString();
        bankACCNumber = individualBankAccNo.getEditText().getText().toString();
        village=individualVillage.getEditText().getText().toString();
        mandal=individualMandal.getEditText().getText().toString();
        district=individualDistrict.getEditText().getText().toString();
        bankIFSC=individualBankIFSC.getEditText().getText().toString();
//        vendorBankAccount=individualVendorBankAccountNumber.getEditText().toString();
//        vendorName=individualBankName.getEditText().toString();
//        vendorBankIFSC=individualBankIFSC.getEditText().toString();
//        if(collectorApproved.equals("yes"))
//        {
//            uploadImage.setEnabled(true);
//        }

        updateData(aadharNumber,indivName,fatherName,age,houseNumber,mobileNumber,preferredunit, bankName,bankACCNumber,my_url);
    }

    public void updateData(String aadharNumber,String name,String fname, String age,String houseNo,String mobileNumber,String preferredUnit,String bankName,String bankACCnumber,String my_url){
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("name", name.trim());
        individualInfo.put("fatherName", fname.trim());
        individualInfo.put("age", age.trim());
        individualInfo.put("houseNo", houseNo.trim());
        individualInfo.put("phoneNo", mobileNumber.trim());
        individualInfo.put("preferredUnit", preferredUnit.trim());
        individualInfo.put("bankName", bankName.trim());
        individualInfo.put("bankAccNo", bankACCnumber.trim());
        individualInfo.put("preferredUnit", preferredUnit.trim());
        individualInfo.put("bankName", bankName.trim());
        individualInfo.put("bankAccNo", bankACCnumber.trim());
//        individualInfo.put("vendorName", vendorName.trim());
//        individualInfo.put("vendorAccountNo", vendorBankAccount.trim());
//        individualInfo.put("vendorIFSC", vendorBankIFSC.trim());
        individualInfo.put("village", village.trim());
        individualInfo.put("mandal", mandal.trim());
        individualInfo.put("district", district.trim());
        individualInfo.put("grounding_img", my_url.trim());
//        individualInfo.put("groundingStatus", groundingStatus);



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

                                             Toast.makeText(userDetails.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                             Intent i = new Intent(userDetails.this, PSAddEdit.class);
                                             i.putExtra("village",village.trim());
                                             i.putExtra("mandal", mandal.trim());
                                             i.putExtra("district",district.trim());
                                             startActivity(i);
                                             finish();
                                         }
                                     }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(userDetails.this, "Error occured", Toast.LENGTH_SHORT).show();
                                 }
                             });

                         }else{
                             Toast.makeText(userDetails.this, "Failed", Toast.LENGTH_SHORT).show();
                         }
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        pgsBar.setVisibility(View.VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            image_uri = data.getData();
            final String timestamp = ""+System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePUSHID = timestamp;
            Toast.makeText(userDetails.this, image_uri.toString(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(userDetails.this,"File Uploaded Successfully",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(my_url));
//                        startActivity(i);
                    }else{
                        Toast.makeText(userDetails.this,"Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}

