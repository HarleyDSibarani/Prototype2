package com.example.prototype2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AnotherUser_HelpForm extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private StorageReference STkoneksi;
    EditText helpname, helpnohp, helpemail,helpinformasi,pointlongitude, pointlatitude,detailalamat,jarak;
    CheckBox isSepi, isRamai;
    Button submit;
    String userId,storageUrl;
    Boolean valid = true;
    private Uri filepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user__help_form);

        client = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        STkoneksi = FirebaseStorage.getInstance().getReference();

        helpname = findViewById(R.id.helpfullname);
        helpnohp = findViewById(R.id.Regisnohp);
        helpemail = findViewById(R.id.helpemail);
        helpemail = findViewById(R.id.helpemail);
        helpinformasi = findViewById(R.id.helpinformasi);
        detailalamat = findViewById(R.id.helpalamat);
        pointlatitude = findViewById(R.id.pointlatitude);
        pointlongitude = findViewById(R.id.pointlongitude);
        isSepi = findViewById(R.id.isSepi);
        isRamai = findViewById(R.id.isRamai);
        submit = findViewById(R.id.submit);

        userId = fAuth.getCurrentUser().getUid();



        if (ActivityCompat.checkSelfPermission(AnotherUser_HelpForm.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(AnotherUser_HelpForm.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                helpname.setText(documentSnapshot.getString("Fullname"));
                helpnohp.setText(documentSnapshot.getString("PhoneNumber"));
                helpemail.setText(documentSnapshot.getString("UserEmail"));
            }
        });

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(helpinformasi);
                checkField(detailalamat);

                if (!(isSepi.isChecked() || isRamai.isChecked())) {
                    Toast.makeText(AnotherUser_HelpForm.this, "Pilih Kondisi Sekitar", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valid){
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Needhelp").document(user.getUid());
                    Map<String,Object> userInfo = new HashMap<>();
                    userInfo.put("NamaLengkap",helpname.getText().toString());
                    userInfo.put("NoHP",helpnohp.getText().toString());
                    userInfo.put("Email",helpemail.getText().toString());
                    userInfo.put("Keterangan",helpinformasi.getText().toString());
                    userInfo.put("AlamatDetail",detailalamat.getText().toString());
                    userInfo.put("Longitude",pointlongitude.getText().toString());
                    userInfo.put("Latitude",pointlatitude.getText().toString());
                    userInfo.put("IDhelp",userId);

                    if (isSepi.isChecked()) {
                        userInfo.put("Kondisi","Sepi");
                    }

                    if (isRamai.isChecked()) {
                        userInfo.put("Kondisi","Ramai");
                    }

                    df.set(userInfo);

                    if (isSepi.isChecked()) {
                        startActivity(new Intent(getApplicationContext(), AnotherUser_HelpForm.class));
                        finish();
                    }

                    if (isRamai.isChecked()) {
                        startActivity(new Intent(getApplicationContext(), AnotherUser_HelpForm.class));
                        finish();
                    }

                    Toast.makeText(AnotherUser_HelpForm.this, "Permintaan Bantuan Terkirim", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Reguler_Menu.class));

                    finish();
                }
            }
        });
    }


        private void getCurrentLocation() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AnotherUser_HelpForm.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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
                textField.setError("isi dengan benar");
                valid = false;
            }else {
                valid = true;
            }
            return valid;
        }


    }