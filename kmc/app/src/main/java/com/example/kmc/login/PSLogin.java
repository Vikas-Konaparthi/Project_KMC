package com.example.kmc.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kmc.PSLogin.PSZone;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class PSLogin extends AppCompatActivity {
    private TextInputLayout username;
    private TextInputLayout password;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pslogin);

    }

    public void submitButton(View view) {
        username  = (TextInputLayout) findViewById(R.id.username);
        password  = (TextInputLayout) findViewById(R.id.password);
        // click handling code
        String uname= username.getEditText().getText().toString();
        String pass= password.getEditText().getText().toString();
        Log.d("myTag", uname);
        DocumentReference document=db.collection("psofficer").document(uname.trim());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    if(pass.equals(documentSnapshot.getString("password"))){
                        Intent i = new Intent(PSLogin.this, PSZone.class);
                        i.putExtra("zone",documentSnapshot.getString("zone"));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    }else{
                        Toast.makeText(PSLogin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PSLogin.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PSLogin.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
            }
        });
    }
}