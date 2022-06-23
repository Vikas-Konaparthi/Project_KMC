package com.example.kmc.PSLogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kmc.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class addIndividual extends AppCompatActivity {
    public TextInputLayout IndividualName;
    public TextInputLayout FatherName;
    public TextInputLayout Age;
    public TextInputLayout HouseNumber;
    public TextInputLayout Village;
    public TextInputLayout Mandal;
    public TextInputLayout District;
    public TextInputLayout AadharNumber;
    public TextInputLayout MobileNumber;
    public TextInputLayout Preferredunit;
    public TextInputLayout BankName;
    public TextInputLayout BankACCNumber;
    public TextInputLayout BankIFSC;
    public TextInputLayout DbBankName;
    public TextInputLayout DbBankACCNumber;
    public TextInputLayout DbBankIFSC;
    //    private TextInputEditText individualVendorName;
//    private TextInputEditText individualVendorBankAccountNumber;
//    private TextInputEditText individualVendorBankIFSC;
    ProgressBar pgsBar;


    FirebaseFirestore db;
    String my_url;
    String individualName;
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
    String bankIFSCNumber;
    String dbBankName;
    String dbBankACCNumber;
    String dbBankIFSC;
//    String vendorName;
//    String vendorBankAccount;
//    String vendorBankIFSC;

    String v;
    String d;
    String m;

    StorageReference storageReference;
    Uri image_uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_individual);
        db=FirebaseFirestore.getInstance();
        pgsBar = (ProgressBar)findViewById(R.id.pBar);
        IndividualName  = (TextInputLayout) findViewById(R.id.IndividualName);
        FatherName  = (TextInputLayout) findViewById(R.id.FatherName);
        Age  = (TextInputLayout) findViewById(R.id.Age);
        HouseNumber  = (TextInputLayout) findViewById(R.id.HouseNumber);
        Village  = (TextInputLayout) findViewById(R.id.village);
        Mandal  = (TextInputLayout) findViewById(R.id.mandal);
        District  = (TextInputLayout) findViewById(R.id.district);
        AadharNumber = (TextInputLayout) findViewById(R.id.AadharNumber);
        MobileNumber = (TextInputLayout) findViewById(R.id.MobileNumber);
        Preferredunit = (TextInputLayout) findViewById(R.id.Preferredunit);
        BankName  = (TextInputLayout) findViewById(R.id.BankName);
        BankACCNumber  = (TextInputLayout) findViewById(R.id.BankACCNumber);
        BankIFSC  = (TextInputLayout) findViewById(R.id.BankIFSC);
        DbBankName  = (TextInputLayout) findViewById(R.id.DbBankName);
        DbBankACCNumber  = (TextInputLayout) findViewById(R.id.DbBankACCNumber);
        DbBankIFSC  = (TextInputLayout) findViewById(R.id.DbBankIFSC);

//        individualVendorName=(TextInputEditText) findViewById(R.id.vendorName);
//        individualVendorBankAccountNumber=(TextInputEditText) findViewById(R.id.vendorBankAccountNo);
//        individualVendorBankIFSC=(TextInputEditText) findViewById(R.id.vendorBankIFSC);
        storageReference= FirebaseStorage.getInstance().getReference();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            v = extras.getString("village");
            m=extras.getString("mandal");
            d=extras.getString("district");
            //The key argument here must match that used in the other activity
        }
        Village.getEditText().setText(v);
        Mandal.getEditText().setText(m);
        District.getEditText().setText(d);

    }


    public void submitButton(View view) {
        individualName = IndividualName.getEditText().getText().toString();
        fatherName = FatherName.getEditText().getText().toString();
        age = Age.getEditText().getText().toString();
        houseNumber = HouseNumber.getEditText().getText().toString();
        village = Village.getEditText().getText().toString();
        mandal = Mandal.getEditText().getText().toString();
        district = District.getEditText().getText().toString();
        aadharNumber = AadharNumber.getEditText().getText().toString();
        mobileNumber = MobileNumber.getEditText().getText().toString();
        preferredunit = Preferredunit.getEditText().getText().toString();
        bankName = BankName.getEditText().getText().toString();
        bankACCNumber = BankACCNumber.getEditText().getText().toString();
        bankIFSCNumber=BankIFSC.getEditText().getText().toString();
        dbBankName = DbBankName.getEditText().getText().toString();
        dbBankACCNumber = DbBankACCNumber.getEditText().getText().toString();
        dbBankIFSC=DbBankIFSC.getEditText().getText().toString();
//        vendorName= individualVendorName.getText().toString();
//        vendorBankAccount= individualVendorBankAccountNumber.getText().toString();
//        vendorBankIFSC= individualVendorBankIFSC.getText().toString();
        //
        if (individualName.length() != 0 && fatherName.length() != 0 && age.length() != 0 && houseNumber.length() != 0 && aadharNumber.length() != 0 && mobileNumber.length() != 0 && preferredunit.length() != 0 && bankName.length() != 0 && bankACCNumber.length() != 0 && dbBankName.length() != 0 && dbBankACCNumber.length() != 0 && dbBankIFSC.length() != 0 ) {
            Map<String, Object> individualInfo = new HashMap<String, Object>();
            individualInfo.put("name", individualName.trim());
            individualInfo.put("fatherName", fatherName.trim());
            individualInfo.put("age", age.trim());
            individualInfo.put("houseNo", houseNumber.trim());
            individualInfo.put("village", village.trim());
            individualInfo.put("mandal", mandal.trim());
            individualInfo.put("district", district.trim());
            individualInfo.put("aadhar", aadharNumber.trim());
            individualInfo.put("phoneNo", mobileNumber.trim());
            individualInfo.put("preferredUnit", preferredunit.trim());
            individualInfo.put("bankName", bankName.trim());
            individualInfo.put("bankAccNo", bankACCNumber.trim());
            individualInfo.put("bankIFSC", bankIFSCNumber.trim());
            individualInfo.put("dbBankName", dbBankName.trim());
            individualInfo.put("dbBankAccNo", dbBankACCNumber.trim());
            individualInfo.put("dbBankIFSC", dbBankIFSC.trim());
//            individualInfo.put("vendorName", vendorName.trim());
//            individualInfo.put("vendorAccountNo", vendorBankAccount.trim());
//            individualInfo.put("vendorIFSC", vendorBankIFSC.trim());
            //
            individualInfo.put("individualAmountRequired", "");
            individualInfo.put("spAmountApproved", "");
            individualInfo.put("dbAccount", "0");
            individualInfo.put("psUpload", my_url);
            individualInfo.put("spApproved", "");
            individualInfo.put("spApproved2", "");
            individualInfo.put("spApproved3", "");
            individualInfo.put("ctrApproved", "");
            individualInfo.put("secOfficerApproved", "");
            individualInfo.put("so_remarks", "");
            individualInfo.put("sp_remarks", "");
            individualInfo.put("grounding_img", "");
            individualInfo.put("status", "Waiting for Special Officer Approval");
            individualInfo.put("approvalAmount", "0");
            individualInfo.put("groundingStatus", "");
            individualInfo.put("vendorName", "");
            individualInfo.put("vendorAccountNo", "");
            individualInfo.put("vendorIFSC", "");
            individualInfo.put("vendorAgency", "");
            individualInfo.put("vendorBankName", "");
            individualInfo.put("quotationImage", "");
            individualInfo.put("psApprovedAmount", "");
            individualInfo.put("soApproved", "");
            individualInfo.put("so_quotation_amount", "");
            individualInfo.put("ctrApproved2", "");
            individualInfo.put("psApproved", "");
            individualInfo.put("psApproved2", "");
            individualInfo.put("psApproved3", "");


            //
            db.collection("individuals").add(individualInfo)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(addIndividual.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(addIndividual.this, PSAddEdit.class);
                            i.putExtra("village",village.trim());
                            i.putExtra("mandal", mandal.trim());
                            i.putExtra("district",district.trim());
                            startActivity(i);
                            finish();
                        }
                    });
        } else {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadButton(View view) {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        pgsBar.setVisibility(View.VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            image_uri = data.getData();
            final String timestamp = ""+System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePUSHID = timestamp;
            Toast.makeText(addIndividual.this, image_uri.toString(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(addIndividual.this,"File Uploaded Successfully",Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(my_url));
//                        startActivity(i);

                    }else{
                        Toast.makeText(addIndividual.this,"Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}