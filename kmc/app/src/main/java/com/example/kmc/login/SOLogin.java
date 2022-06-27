package com.example.kmc.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kmc.R;
import com.example.kmc.SOLogin.SOListOfBen;
import com.example.kmc.SOLogin.SO_Action;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SOLogin extends AppCompatActivity {
    private TextInputLayout username;
    private TextInputLayout password;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sologin);
    }
    public void submitButton(View view) {
        username  = (TextInputLayout) findViewById(R.id.username);
        password  = (TextInputLayout) findViewById(R.id.password);
        // click handling code
        String uname= username.getEditText().getText().toString();
        String pass= password.getEditText().getText().toString();

        DocumentReference document=db.collection("sectionofficer").document(uname.trim());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    if(pass.equals(documentSnapshot.getString("password"))){
                        Intent i = new Intent(SOLogin.this, SO_Action.class);
                        i.putExtra("mandal",documentSnapshot.getString("mandal"));
                        i.putExtra("sector",documentSnapshot.getString("sector"));
                        i.putExtra("aadhar",documentSnapshot.getString("aadhar"));
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(SOLogin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SOLogin.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SOLogin.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
            }
        });
    }
}