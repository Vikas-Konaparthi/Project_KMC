package com.example.kmc.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kmc.CLogin.CollectorMandalSelection;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Collector_Login extends AppCompatActivity {
    private TextInputLayout username;
    private TextInputLayout password;
    FirebaseFirestore db;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_login);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    public void submitButton(View view) {
        username  = (TextInputLayout) findViewById(R.id.username);
        password  = (TextInputLayout) findViewById(R.id.password);
        // click handling code
        String uname= username.getEditText().getText().toString();
        String pass= password.getEditText().getText().toString();

        DocumentReference document=db.collection("collector").document(uname.trim());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    if(pass.equals(documentSnapshot.getString("password"))){
                        Intent i = new Intent(Collector_Login.this, CollectorMandalSelection.class);
                        i.putExtra("district",documentSnapshot.getString("District"));
                        i.putExtra("aadhar",documentSnapshot.getString("aadhar"));

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("aadhar",documentSnapshot.getString("aadhar"));
                        editor.apply();

                        startActivity(i);
                        finish();

                    }else{
                        Toast.makeText(Collector_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Collector_Login.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Collector_Login.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
            }
        });
    }
}