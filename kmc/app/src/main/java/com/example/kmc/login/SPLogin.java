package com.example.kmc.login;

import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences pref;
    SharedPreferences.Editor editor2;
    String spUname;
    String spPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splogin);
        username  = (TextInputLayout) findViewById(R.id.username);
        password  = (TextInputLayout) findViewById(R.id.password);
        pref = getApplicationContext().getSharedPreferences("SP", 0); // 0 - for private mode
        editor2 = pref.edit();
        spUname=pref.getString("spUsername", "");
        spPassword=pref.getString("spPassword", "");

        if (!spUname.equals("")||!spPassword.equals(""))
        {
            username.getEditText().setText(spUname);
            password.getEditText().setText(spPassword);
        }else{
            username.getEditText().setText("");
            password.getEditText().setText("");
        }

    }

    public void submitButton(View view) {

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
                        editor2.putString("spUsername", uname.trim()); // Storing string
                        editor2.putString("spPassword", pass.trim());
                        editor2.commit();
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