package com.example.prototype2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototype2.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    Button loginbutton;
    TextView forgotpasswordbutton, registerbutton, polisiloginbutton;
    EditText loginemail, loginpassword;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        loginbutton = findViewById(R.id.loginbtn);
        registerbutton = findViewById(R.id.regisbtn);
        forgotpasswordbutton = findViewById(R.id.forgotpassbtn);
        loginemail = findViewById(R.id.loginEmail);
        loginpassword = findViewById(R.id.loginPassword);
        polisiloginbutton = findViewById(R.id.loginpolicebtn);

        registerbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Register.class));
                    }
                });

        polisiloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginPolisi.class));
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(loginemail);
                checkField(loginpassword);
                Log.d("TAG", "onClick: "+ loginemail.getText().toString());

                if (valid){
                    fAuth.signInWithEmailAndPassword(loginemail.getText().toString(),loginpassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()){
                                checkUserAccessLevel(authResult.getUser().getUid());
                                finish();
                            }
                            else {
                                user.sendEmailVerification();
                                Toast.makeText(Login.this,"Periksa Email Untuk Verifikasi", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });

                }

            }

        });
    }


    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        //extract data from document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+ documentSnapshot.getData());
                // identify user access level

                if(documentSnapshot.getString("isReguler") != null){
                    startActivity(new Intent(getApplicationContext(),Reguler_Menu.class));
                    finish();
                }

                if(documentSnapshot.getString("isWorkshop") != null){
                    startActivity(new Intent(getApplicationContext(),Workshop_Menu.class));
                    finish();
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