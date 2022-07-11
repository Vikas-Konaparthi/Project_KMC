package com.example.kmc.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kmc.PSLogin.PS_Action;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PSLogin extends AppCompatActivity {
    private TextInputLayout username;
    private TextInputLayout password;
    FirebaseFirestore db;
    SharedPreferences pref;
    SharedPreferences.Editor editor2;
    String psUname;
    String psPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pslogin);
        pref = getApplicationContext().getSharedPreferences("PS", 0); // 0 - for private mode
        editor2 = pref.edit();
        username  = (TextInputLayout) findViewById(R.id.username);
        password  = (TextInputLayout) findViewById(R.id.password);

        psUname=pref.getString("psUsername", "");
        psPassword=pref.getString("psPassword", "");

        if (!psUname.equals("")||!psPassword.equals(""))
        {
            username.getEditText().setText(psUname);
            password.getEditText().setText(psPassword);
        }else{
            username.getEditText().setText("");
            password.getEditText().setText("");
        }

    }

    public void submitButton(View view) {
        username  = (TextInputLayout) findViewById(R.id.username);
        password  = (TextInputLayout) findViewById(R.id.password);
        // click handling code
        String uname= username.getEditText().getText().toString();
        String pass= password.getEditText().getText().toString();
        DocumentReference document=db.collection("psofficer").document(uname.trim());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    if(pass.equals(documentSnapshot.getString("password"))){
                        Intent i = new Intent(PSLogin.this, PS_Action.class);
                        i.putExtra("village",documentSnapshot.getString("village"));
                        i.putExtra("mandal",documentSnapshot.getString("mandal"));
                        i.putExtra("district",documentSnapshot.getString("district"));
                        i.putExtra("uname",uname);
                        i.putExtra("aadhar",documentSnapshot.getString("aadhar"));
                        editor2.putString("psUsername", uname.trim()); // Storing string
                        editor2.putString("psPassword", pass.trim());
                        editor2.commit();
                        startActivity(i);
                        finish();

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