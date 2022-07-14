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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kmc.District;
import com.example.kmc.R;
import com.example.kmc.Unit;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addIndividual extends AppCompatActivity {
    public TextInputLayout IndividualName;
    public TextInputLayout FatherName;
    public TextInputLayout Age;
    public TextInputLayout HouseNumber;
    public Spinner Constituency;
    public TextInputLayout Village;
    public TextInputLayout Mandal;
    public TextInputLayout District;
    public TextInputLayout AadharNumber;
    public TextInputLayout MobileNumber;
    public Spinner Preferredunit;
    public TextInputLayout BankName;
    public TextInputLayout BankACCNumber;
    public TextInputLayout BankIFSC;
    public TextInputLayout DbBankName;
    public TextInputLayout DbBankACCNumber;
    public TextInputLayout DbBankIFSC;
    public TextInputLayout Occupation;
    public TextInputLayout Rationcardnumber;

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
    String occupation;
    String rationcardnumber;
//    String vendorName;
//    String vendorBankAccount;
//    String vendorBankIFSC;

    String v;
    String d;
    String m;

    Unit obj;
    List<DocumentSnapshot> list;
    String option_selected;
    String const_selected;


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
        Preferredunit = (Spinner) findViewById(R.id.spinner_preferredunit);
        Constituency = (Spinner) findViewById(R.id.spinner_constituency);
        BankName  = (TextInputLayout) findViewById(R.id.BankName);
        BankACCNumber  = (TextInputLayout) findViewById(R.id.BankACCNumber);
        BankIFSC  = (TextInputLayout) findViewById(R.id.BankIFSC);
        DbBankName  = (TextInputLayout) findViewById(R.id.DbBankName);
        DbBankACCNumber  = (TextInputLayout) findViewById(R.id.DbBankACCNumber);
        DbBankIFSC  = (TextInputLayout) findViewById(R.id.DbBankIFSC);
        Occupation=(TextInputLayout) findViewById(R.id.Occupation);
        Rationcardnumber=(TextInputLayout) findViewById(R.id.Rationcardnumber);

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

        List<String> units = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Preferredunit.setAdapter(adapter);

        List<String> constituencies = new ArrayList<>();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                constituencies);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Constituency.setAdapter(adapter1);

        db.collection(d+"_constituencies").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {

                            District obj1 = d.toObject(District.class);

                            obj1.setUid(d.getId().toString());
                            constituencies.add(obj1.getUid());
                        }
                        adapter1.notifyDataSetChanged();
                        const_selected=adapter1.getItem(0);
                    }

                });

        Constituency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {

                Constituency.setSelection(position);
                const_selected = Constituency.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        db.collection("unit").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {

                            obj = d.toObject(Unit.class);

                            units.add(obj.getUnitName());
                        }
                        adapter.notifyDataSetChanged();
                        option_selected = adapter.getItem(0);
                    }

                });

        Preferredunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {

                Preferredunit.setSelection(position);
                option_selected = Preferredunit.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });



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
//        preferredunit = Preferredunit.getEditText().getText().toString();
        bankName = BankName.getEditText().getText().toString();
        bankACCNumber = BankACCNumber.getEditText().getText().toString();
        bankIFSCNumber=BankIFSC.getEditText().getText().toString();
        dbBankName = DbBankName.getEditText().getText().toString();
        dbBankACCNumber = DbBankACCNumber.getEditText().getText().toString();
        dbBankIFSC=DbBankIFSC.getEditText().getText().toString();
        occupation=Occupation.getEditText().getText().toString();
        rationcardnumber=Rationcardnumber.getEditText().getText().toString();
//        vendorName= individualVendorName.getText().toString();
//        vendorBankAccount= individualVendorBankAccountNumber.getText().toString();
//        vendorBankIFSC= individualVendorBankIFSC.getText().toString();
        //
        if (individualName.length() != 0 && fatherName.length() != 0 && age.length() != 0 && houseNumber.length() != 0 && aadharNumber.length() != 0 && mobileNumber.length() != 0 && option_selected.length() != 0 && bankName.length() != 0 && bankACCNumber.length() != 0 && dbBankName.length() != 0 && dbBankACCNumber.length() != 0 && dbBankIFSC.length() != 0 ) {
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
            individualInfo.put("preferredUnit", option_selected.trim());
            individualInfo.put("bankName", bankName.trim());
            individualInfo.put("bankAccNo", bankACCNumber.trim());
            individualInfo.put("bankIFSC", bankIFSCNumber.trim());
            individualInfo.put("dbBankName", dbBankName.trim());
            individualInfo.put("dbBankAccNo", dbBankACCNumber.trim());
            individualInfo.put("dbBankIFSC", dbBankIFSC.trim());
            individualInfo.put("occupation", occupation.trim());
            individualInfo.put("rationcardnumber", rationcardnumber.trim());
            individualInfo.put("constituency", const_selected.trim());
//            individualInfo.put("vendorName", vendorName.trim());
//            individualInfo.put("vendorAccountNo", vendorBankAccount.trim());
//            individualInfo.put("vendorIFSC", vendorBankIFSC.trim());
            //
            individualInfo.put("individualAmountRequired", "NA");
            individualInfo.put("spAmountApproved", "NA");
            individualInfo.put("dbAccount", "0");
            individualInfo.put("psUpload", my_url);
            individualInfo.put("spApproved", "NA");
            individualInfo.put("spApproved2", "NA");
            individualInfo.put("spApproved3", "NA");
            individualInfo.put("ctrApproved", "NA");
            individualInfo.put("secOfficerApproved", "NA");
            individualInfo.put("so_remarks", "NA");
            individualInfo.put("sp_remarks", "NA");
            individualInfo.put("grounding_img", "NA");
            individualInfo.put("status", "Waiting for Special Officer Approval");
            individualInfo.put("approvalAmount", "0");
            individualInfo.put("groundingStatus", "NA");
            individualInfo.put("vendorName", "NA");
            individualInfo.put("vendorAccountNo", "NA");
            individualInfo.put("vendorIFSC", "NA");
            individualInfo.put("vendorAgency", "NA");
            individualInfo.put("vendorBankName", "NA");
            individualInfo.put("quotationImage", "NA");
            individualInfo.put("psApprovedAmount", "NA");
            individualInfo.put("soApproved", "NA");
            individualInfo.put("so_quotation_amount", "NA");
            individualInfo.put("ctrApproved2", "NA");
            individualInfo.put("psApproved", "NA");
            individualInfo.put("psApproved2", "NA");
            individualInfo.put("psApproved3", "NA");
            individualInfo.put("ctrNote1", "NA");
            individualInfo.put("ctrNote2", "NA");
            individualInfo.put("spNote", "NA");

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