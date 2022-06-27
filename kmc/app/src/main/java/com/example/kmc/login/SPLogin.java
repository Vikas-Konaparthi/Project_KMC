package com.example.kmc.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kmc.R;
import com.example.kmc.SPLogin.ListOfBen;
import com.example.kmc.SPLogin.SP_Action;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SPLogin extends AppCompatActivity {
    private TextInputLayout username;
    private TextInputLayout password;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splogin);

    }

    public void submitButton(View view) {
        username  = (TextInputLayout) findViewById(R.id.username);
        password  = (TextInputLayout) findViewById(R.id.password);
        // click handling code
        String uname= username.getEditText().getText().toString();
        String pass= password.getEditText().getText().toString();
        Log.d("myTag", uname);
        DocumentReference document=db.collection("spofficer").document(uname.trim());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    if(pass.equals(documentSnapshot.getString("password"))){
                        Intent i = new Intent(SPLogin.this, SP_Action.class);
                        i.putExtra("village1",documentSnapshot.getString("village1"));
                        i.putExtra("village2",documentSnapshot.getString("village2"));
                        i.putExtra("aadhar",documentSnapshot.getString("aadhar"));
                        startActivity(i);
                        finish();

                    }else{
                        Toast.makeText(SPLogin.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SPLogin.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SPLogin.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
            }
        });
    }
}