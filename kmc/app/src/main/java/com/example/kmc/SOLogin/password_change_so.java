package com.example.kmc.SOLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kmc.PSLogin.PS_Action;
import com.example.kmc.PSLogin.password_change;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class password_change_so extends AppCompatActivity {
    String uname;
    String village;
    String sector;
    String mandal;
    String pass;
    String cnfPass;
    String aadhar;
    public TextInputLayout password;
    public TextInputLayout cnfPassword;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change_so);
        db = FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        password = (TextInputLayout) findViewById(R.id.pass);
        cnfPassword = (TextInputLayout) findViewById(R.id.cnfPass);
        if (extras != null) {
            String value = extras.getString("mandal");
            String value2 = extras.getString("sector");
            //String value3 = extras.getString("preferredUnit");
            //The key argument here must match that used in the other activity
            mandal = value;
            sector = value2;
            aadhar=extras.getString("aadhar");
        } else {
            Log.d("extra", "no");
        }
    }

    public void changePassword(View view) {
        pass = password.getEditText().getText().toString();
        cnfPass = cnfPassword.getEditText().getText().toString();
        Log.d("Pass", pass);
        Log.d("PassCnf", cnfPass);
        if (pass.equals(cnfPass)) {

            Log.d("aa1",aadhar);
            update(pass);
        } else
            Toast.makeText(this, "Both Fields Are Not Same", Toast.LENGTH_LONG).show();
    }

    public void update(String pass) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("password", pass);
        Log.d("aa",aadhar);
        db.collection("sectionofficer").whereEqualTo("aadhar", aadhar)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();
                    db.collection("sectionofficer")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(password_change_so.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(password_change_so.this, SO_Action.class);
                                    i.putExtra("sector",sector);
                                    i.putExtra("mandal",mandal);
                                    i.putExtra("aadhar",aadhar);
                                    startActivity(i);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(password_change_so.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(password_change_so.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}