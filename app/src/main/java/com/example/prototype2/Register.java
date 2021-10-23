package com.example.prototype2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototype2.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    StorageReference STkoneksi;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    TextView loginnow;
    EditText hasilrole, mRegisFullname, mRegisnohp, mRegisEmail, mRegisPassword;
    Button Registerbtn;
    CheckBox isRegulerBox, isWorkshopBox;
    boolean valid = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        STkoneksi = FirebaseStorage.getInstance().getReference();

        loginnow = findViewById(R.id.loginnowbtn);
        hasilrole = findViewById(R.id.hasilroleuser);
        mRegisFullname = findViewById(R.id.RegisFullname);
        mRegisnohp = findViewById(R.id.Regisnohp);
        mRegisEmail = findViewById(R.id.RegisEmail);
        mRegisPassword = findViewById(R.id.RegisPassword);
        Registerbtn = findViewById(R.id.Registerbtn);
        progressBar = findViewById(R.id.progressbar);
        isRegulerBox = findViewById(R.id.isReguler);
        isWorkshopBox = findViewById(R.id.isWorkshop);

        loginnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        isRegulerBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()) {
                    isWorkshopBox.setChecked(false);
                }
            }
        });


        isWorkshopBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()) {
                    isRegulerBox.setChecked(false);
                }
            }
        });

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(mRegisFullname);
                checkField(mRegisEmail);
                checkField(mRegisPassword);
                checkField(mRegisnohp);


                if (!(isRegulerBox.isChecked() || isWorkshopBox.isChecked())) {
                    Toast.makeText(Register.this, "Select the Account Type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valid) {
                    //start the user registrasi process
                    fAuth.createUserWithEmailAndPassword(mRegisEmail.getText().toString(), mRegisPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Akun Terdaftar,Silahkan Cek Email Untuk Verifikasi", Toast.LENGTH_LONG).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Fullname", mRegisFullname.getText().toString());
                            userInfo.put("UserEmail", mRegisEmail.getText().toString());
                            userInfo.put("PhoneNumber", mRegisnohp.getText().toString());
                            // specify if the user admin
                            if (isRegulerBox.isChecked()) {
                                userInfo.put("isReguler","1");
                            }

                            if (isWorkshopBox.isChecked()) {
                                userInfo.put("isWorkshop","1");
                            }

                            df.set(userInfo);
                            user.sendEmailVerification();


                            if (isRegulerBox.isChecked()) {
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            }

                            if (isWorkshopBox.isChecked()) {
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
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