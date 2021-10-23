package com.example.prototype2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Danger_Form extends AppCompatActivity {
    Button sostomenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger__form);

        sostomenu = findViewById(R.id.dangerbacktomenu);

        sostomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Reguler_Menu.class));
                finish();
            }
        });

    }


}