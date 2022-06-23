package com.example.kmc.PSLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.widget.Toast;

import com.example.kmc.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class password_change extends AppCompatActivity {
    String uname;
    String village;
    String district;
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
        setContentView(R.layout.activity_password_change);
        db = FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        password = (TextInputLayout) findViewById(R.id.pass);
        cnfPassword = (TextInputLayout) findViewById(R.id.cnfPass);
        if (extras != null) {
            String value = extras.getString("village");
            village = value;
            mandal = extras.getString("mandal");
            district = extras.getString("district");
            uname = extras.getString("uname");
            aadhar = extras.getString("aadhar");
        } else {
            Log.d("extra", "no");
        }
    }

    public void changePass(View view) {
        pass = password.getEditText().getText().toString();
        cnfPass = cnfPassword.getEditText().getText().toString();
        Log.d("Pass", pass);
        Log.d("PassCnf", cnfPass);
        if (pass.equals(cnfPass)) {

            Log.d("uname3", uname);
            Log.d("aa1",aadhar);
            update(pass, uname);
        } else
            Toast.makeText(this, "Both Fields Are Not Same", Toast.LENGTH_LONG).show();
    }

    public void update(String pass, String uname) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("password", pass);
Log.d("aa",aadhar);
        db.collection("psofficer").whereEqualTo("aadhar", aadhar)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();
                    db.collection("psofficer")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(password_change.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(password_change.this, PS_Action.class);
                                    i.putExtra("village", village.trim());
                                    i.putExtra("mandal", mandal.trim());
                                    i.putExtra("district", district.trim());
                                    i.putExtra("uname", uname.trim());
                                    i.putExtra("aadhar", aadhar.trim());
                                    startActivity(i);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(password_change.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(password_change.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}