package com.example.kmc.SOLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kmc.PSLogin.PSAddEdit;
import com.example.kmc.PSLogin.addIndividual;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addVendor extends AppCompatActivity {
    public TextInputLayout AgencyName;
    public TextInputLayout UnitName;
    public TextInputLayout BankAccount;
    public TextInputLayout BankIFSC;
    public TextInputLayout BankName;
    public TextInputLayout VendorName;
    FirebaseFirestore db;
    String agencyname;
    String unitname;
    String bankaccount;
    String bankifsc;
    String bankname;
    String vendorname;
    String mandal;
    String sector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendor);
        db=FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mandal");
            String value2 = extras.getString("sector");
            //String value3 = extras.getString("preferredUnit");
            //The key argument here must match that used in the other activity
            mandal = value;
            sector = value2;
            //preferredUnit = value3;

        }else{
            Log.d("extra", "no");
        }
        AgencyName  = (TextInputLayout) findViewById(R.id.AgencyName);
        UnitName  = (TextInputLayout) findViewById(R.id.UnitName);
        BankAccount  = (TextInputLayout) findViewById(R.id.vendorAgencyaccountnumber);
        BankIFSC  = (TextInputLayout) findViewById(R.id.vendorBankIFSC);
        BankName  = (TextInputLayout) findViewById(R.id.vendorBankName);
        VendorName  = (TextInputLayout) findViewById(R.id.vendorName);
        UnitName.getEditText().setText(sector);
    }

    public void AddVendors(View view) {
        agencyname = AgencyName.getEditText().getText().toString();
        bankaccount = BankAccount.getEditText().getText().toString();
        bankifsc = BankIFSC.getEditText().getText().toString();
        bankname = BankName.getEditText().getText().toString();
        vendorname = VendorName.getEditText().getText().toString();
     if(agencyname.length()!=0&&bankaccount.length()!=0&&bankifsc.length()!=0&&bankname.length()!=0&&vendorname.length()!=0)
     {
         Map<String, Object> VendorsInfo = new HashMap<String, Object>();
         VendorsInfo.put("agencyName", agencyname.toLowerCase().trim());
         VendorsInfo.put("unit", unitname.trim());
         VendorsInfo.put("vendorBankAcc", bankaccount.trim());
         VendorsInfo.put("vendorBankIFSC", bankifsc.trim());
         VendorsInfo.put("vendorBankName", bankname.trim());
         VendorsInfo.put("vendorName", vendorname.trim());

         db.collection("vendorAgency").add(VendorsInfo)
                 .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentReference> task) {
                         Toast.makeText(addVendor.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                         Intent i = new Intent(addVendor.this, addVendor.class);
                         i.putExtra("vendorAgency",agencyname.trim());
                         i.putExtra("UnitName",unitname.trim());
                         i.putExtra("VendorBankName", bankaccount.trim());
                         i.putExtra("VendorIFSC",bankifsc.trim());
                         i.putExtra("VendorBankName",bankname.trim());
                         i.putExtra("VendorName",vendorname.trim());
                         startActivity(i);
                         finish();
                     }
                 });
     }else{
         Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();

     }

    }
}