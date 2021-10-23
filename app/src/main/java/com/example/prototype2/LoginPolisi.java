package com.example.prototype2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPolisi extends AppCompatActivity {
    Button loginbuttonpolisi;
    EditText loginemailpolisi, loginpasswordpolisi;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_polisi);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        loginbuttonpolisi = findViewById(R.id.loginbtnpolisi);
        loginemailpolisi = findViewById(R.id.loginEmailPolisi);
        loginpasswordpolisi = findViewById(R.id.loginPasswordPolisi);

        loginbuttonpolisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: "+ loginemailpolisi.getText().toString());
                checkField(loginemailpolisi);
                checkField(loginpasswordpolisi);

                if (valid){
                    fAuth.signInWithEmailAndPassword(loginemailpolisi.getText().toString(),loginpasswordpolisi.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginPolisi.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Police_Menu.class));
                            }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginPolisi.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });

                }

            }

        });
    }

    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("isi dengan benar");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}