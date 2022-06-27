package com.example.kmc.SPLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kmc.PSLogin.PS_Action;
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

public class password_change_sp extends AppCompatActivity {
    String uname;
    String village2;
    String village1;
    String pass;
    String cnfPass;
    String aadhar;
    public TextInputLayout password;
    public TextInputLayout cnfPassword;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change_sp);
        db = FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        password = (TextInputLayout) findViewById(R.id.pass);
        cnfPassword = (TextInputLayout) findViewById(R.id.cnfPass);
        if (extras != null) {
            village1 = extras.getString("village1");
            village2 = extras.getString("village2");
            aadhar = extras.getString("aadhar");
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

            update(pass, uname);
        } else
            Toast.makeText(this, "Both Fields Are Not Same", Toast.LENGTH_LONG).show();
    }

    public void update(String pass, String uname) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("password", pass);
Log.d("aa",aadhar);
        db.collection("spofficer").whereEqualTo("aadhar", aadhar)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();
                    db.collection("spofficer")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(password_change_sp.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(password_change_sp.this, SP_Action.class);
                                    i.putExtra("village1",village1);
                                    i.putExtra("village2",village2);
                                    i.putExtra("aadhar",aadhar);
                                    startActivity(i);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(password_change_sp.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(password_change_sp.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}