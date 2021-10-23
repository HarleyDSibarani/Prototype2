package com.example.prototype2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Reguler_Menu extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CardView formfinduser, finduserhelp, formfindworkshop ,soshelp, daftarbengkel;
    EditText sosnohp, soslongitude, soslatitude;
    String userId;
    TextView namauser;
    Boolean valid = true;
    Double currentLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reguler__menu);

        formfinduser = findViewById(R.id.formfinduser);
        finduserhelp = findViewById(R.id.formfinduserhelp);
        formfindworkshop = findViewById(R.id.formfindworkshop);
        daftarbengkel = findViewById(R.id.DaftarBengkel);

        namauser = findViewById(R.id.namauser);
        soshelp = findViewById(R.id.soshelp);
        sosnohp = findViewById(R.id.sosnohp);
        soslongitude = findViewById(R.id.soslongitude);
        soslatitude = findViewById(R.id.soslatitude);

        client = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        if (ActivityCompat.checkSelfPermission(Reguler_Menu.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(Reguler_Menu.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                namauser.setText(documentSnapshot.getString("Fullname"));
                sosnohp.setText(documentSnapshot.getString("PhoneNumber"));
            }
        });


        formfinduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AnotherUser_HelpForm.class));
            }
        });

        finduserhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),list_userforhelp.class));
            }
        });

        formfindworkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),findworkshop_form.class));
            }
        });

        daftarbengkel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),list_workshop.class));
            }
        });

        soshelp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                checkField(sosnohp);

                if (valid) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("SOS").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("NamaLengkap", namauser.getText().toString());
                    userInfo.put("NoHP", sosnohp.getText().toString());
                    userInfo.put("Longitude", soslongitude.getText().toString());
                    userInfo.put("Latitude", soslatitude.getText().toString());

                    df.set(userInfo);

                    Toast.makeText(Reguler_Menu.this, "Permintaan Bantuan Terkirim", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Danger_Form.class));
                }
                return false;
            }
        });
    }

    public void logoutReguler(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Reguler_Menu.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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

                            soslongitude.setText(Double.toString(latLng.longitude));
                            soslatitude.setText(Double.toString(latLng.latitude));
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