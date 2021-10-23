package com.example.prototype2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuUtama extends AppCompatActivity {
    CardView formfinduser, formfindworkshop, soshelp;
    TextView namauser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        formfinduser = (CardView)findViewById(R.id.formfinduser);
        formfindworkshop= (CardView)findViewById(R.id.formfindworkshop);
        soshelp = (CardView)findViewById(R.id.soshelp);
        namauser = findViewById(R.id.namauser);



        formfinduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AnotherUser_HelpForm.class));
            }
        });

        formfindworkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), add_workshop.class));
            }
        });

        soshelp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getApplicationContext(),Danger_Form.class));
                return true;
            }
        });

    }

    public void logoutReguler(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}