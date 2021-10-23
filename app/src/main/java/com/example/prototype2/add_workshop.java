package com.example.prototype2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class add_workshop extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    EditText namabengkel, regisnohp, helpemail,helpalamat,keterangan,jenisworkshop,pointlongitude, pointlatitude;
    Button submit;
    Boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop);

        client = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        namabengkel = findViewById(R.id.namabengkel);
        regisnohp = findViewById(R.id.Regisnohp);
        helpemail = findViewById(R.id.helpemail);
        keterangan = findViewById(R.id.helpketerangan);
        helpalamat = findViewById(R.id.helpalamat);
        jenisworkshop = findViewById(R.id.helpjenisworkshop);
        submit = findViewById(R.id.submit);
        pointlongitude = findViewById(R.id.pointlongitude);
        pointlatitude = findViewById(R.id.pointlatitude);

        userId = fAuth.getCurrentUser().getUid();

        if (ActivityCompat.checkSelfPermission(add_workshop.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(add_workshop.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                namabengkel.setText(documentSnapshot.getString("Fullname"));
                regisnohp.setText(documentSnapshot.getString("PhoneNumber"));
                helpemail.setText(documentSnapshot.getString("UserEmail"));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(namabengkel);
                checkField(regisnohp);
                checkField(helpemail);
                checkField(keterangan);
                checkField(helpalamat);
                checkField(jenisworkshop);

                if (valid){
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Workshop").document(user.getUid());
                    Map<String,Object> userInfo = new HashMap<>();
                    userInfo.put("NamaBengkel",namabengkel.getText().toString());
                    userInfo.put("NoHP",regisnohp.getText().toString());
                    userInfo.put("Email",helpemail.getText().toString());
                    userInfo.put("Keterangan",keterangan.getText().toString());
                    userInfo.put("JenisBengkel",jenisworkshop.getText().toString());
                    userInfo.put("AlamatDetail",helpalamat.getText().toString());
                    userInfo.put("Longitude",pointlongitude.getText().toString());
                    userInfo.put("Latitude",pointlatitude.getText().toString());

                    df.set(userInfo);

                    Toast.makeText(add_workshop.this, "Bengkel Ditambah", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Workshop_Menu.class));
                    finish();
                }
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(add_workshop.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).title("Disini");
                            pointlongitude.setText(Double.toString(latLng.longitude));
                            pointlatitude.setText(Double.toString(latLng.latitude));


//                            koordinat = "Latitude"+location.getLatitude()+"| Longitude"+location.getLongitude();
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("isi dengan benar");
            valid = false;
        }else {
            valid = true;
        }
        return valid;
    }
}