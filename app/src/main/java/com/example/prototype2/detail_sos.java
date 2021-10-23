package com.example.prototype2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class detail_sos extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    TextView namasos, nohpsos, longitudesos, latitudesos;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sos);

        namasos = findViewById(R.id.detailnamasos);
        nohpsos = findViewById(R.id.detailnohpsos);
        longitudesos = findViewById(R.id.detailLongitudedesos);
        latitudesos = findViewById(R.id.detailLatitudesos);

        fStore = FirebaseFirestore.getInstance();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detailmap);

        ambildata();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Double detailllatitudesos = Double.parseDouble(getIntent().getStringExtra("dlatitude"));
                Double detailllongitudesos = Double.parseDouble(getIntent().getStringExtra("dlongitude"));

                Log.e("Detail Longitude",""+longitudesos);
                Log.e("Detail Latitude",""+latitudesos);

                LatLng latLng = new LatLng(detailllatitudesos,detailllongitudesos);
                MarkerOptions options = new MarkerOptions().position(latLng).title("Disini");

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                googleMap.addMarker(options);
            }
        });

    }

    private void ambildata(){
        if (getIntent().hasExtra("dsosnama") && getIntent().hasExtra("dsosnohp")) {
            String sosnama = getIntent().getStringExtra("dsosnama");
            String sosnohp = getIntent().getStringExtra("dsosnohp");
            String soslatitude = getIntent().getStringExtra("dlatitude");
            String soslongitude = getIntent().getStringExtra("dlongitude");

            namasos.setText(sosnama);
            nohpsos.setText(sosnohp);
            longitudesos.setText(soslongitude);
            latitudesos.setText(soslatitude);
        }

    }
}