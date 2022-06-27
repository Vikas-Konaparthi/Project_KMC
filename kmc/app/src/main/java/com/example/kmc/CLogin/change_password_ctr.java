package com.example.kmc.CLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class change_password_ctr extends AppCompatActivity {

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
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_ctr);
        db = FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        password = (TextInputLayout) findViewById(R.id.pass);
        cnfPassword = (TextInputLayout) findViewById(R.id.cnfPass);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        aadhar = preferences.getString("aadhar","");
        if (extras != null) {
            village = extras.getString("village");
        }
    }

    public void changePass(View view) {
        pass = password.getEditText().getText().toString();
        cnfPass = cnfPassword.getEditText().getText().toString();
        Log.d("Pass", pass);
        Log.d("PassCnf", cnfPass);
        if (pass.equals(cnfPass)) {

            update(pass);
        } else
            Toast.makeText(this, "Both Fields Are Not Same", Toast.LENGTH_LONG).show();
    }

    public void update(String pass) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("password", pass);
        Log.d("aa",aadhar);
        db.collection("collector").whereEqualTo("aadhar", aadhar)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();
                    db.collection("collector")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(change_password_ctr.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(change_password_ctr.this, PS_Action.class);
                                    i.putExtra("village", village.trim());
                                    i.putExtra("aadhar", aadhar.trim());
                                    startActivity(i);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(change_password_ctr.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(change_password_ctr.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}