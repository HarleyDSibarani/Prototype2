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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class findworkshop_form extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    EditText namafindworkshop, nohpfindworkshop, jeniskendaraan, informasifindworkshop, alamatfindworkshop,pointlongitude, pointlatitude;
    Button submit;
    CheckBox isSepi, isRamai;
    String userId;
    Boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findworkshop_form);

        client = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        namafindworkshop = findViewById(R.id.namafindworkshop);
        nohpfindworkshop = findViewById(R.id.nohpfindworkshop);
        jeniskendaraan = findViewById(R.id.jeniskendaraan);
        informasifindworkshop = findViewById(R.id.informasifindworkshop);
        alamatfindworkshop = findViewById(R.id.alamatfindworkshop);
        pointlongitude = findViewById(R.id.pointlongitude);
        pointlatitude = findViewById(R.id.pointlatitude);
        isSepi = findViewById(R.id.isSepi);
        isRamai = findViewById(R.id.isRamai);


        submit = findViewById(R.id.submit);

        userId = fAuth.getCurrentUser().getUid();

        if (ActivityCompat.checkSelfPermission(findworkshop_form.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(findworkshop_form.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        isSepi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()) {
                    isRamai.setChecked(false);
                }
            }
        });

        isRamai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()) {
                    isSepi.setChecked(false);
                }
            }
        });

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                namafindworkshop.setText(documentSnapshot.getString("Fullname"));
                nohpfindworkshop.setText(documentSnapshot.getString("PhoneNumber"));
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(informasifindworkshop);
                checkField(alamatfindworkshop);
                checkField(jeniskendaraan);

                if (!(isSepi.isChecked() || isRamai.isChecked())) {
                    Toast.makeText(findworkshop_form.this, "Pilih Kondisi Sekitar", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valid){
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("needworkshop").document(user.getUid());
                    Map<String,Object> userInfo = new HashMap<>();
                    userInfo.put("NamaLengkap",namafindworkshop.getText().toString());
                    userInfo.put("NoHP",nohpfindworkshop.getText().toString());
                    userInfo.put("JenisKendaraan",jeniskendaraan.getText().toString());
                    userInfo.put("Keterangan",informasifindworkshop.getText().toString());
                    userInfo.put("AlamatDetail",alamatfindworkshop.getText().toString());
                    userInfo.put("Longitude",pointlongitude.getText().toString());
                    userInfo.put("Latitude",pointlatitude.getText().toString());

                    if (isSepi.isChecked()) {
                        userInfo.put("Kondisi","Sepi");
                    }

                    if (isRamai.isChecked()) {
                        userInfo.put("Kondisi","Ramai");
                    }

                    df.set(userInfo);

                    Toast.makeText(findworkshop_form.this, "Permintaan Bantuan Terkirim", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Reguler_Menu.class));
                    finish();
                }
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(findworkshop_form.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }
        return valid;
    }


}