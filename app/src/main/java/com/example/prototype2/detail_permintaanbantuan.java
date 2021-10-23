package com.example.prototype2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.prototype2.model.needhelp;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static android.widget.RelativeLayout.TRUE;

public class detail_permintaanbantuan extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;

    EditText detailnama;
    EditText detailketerangan;
    EditText detailtelepon;
    EditText detailemail;
    EditText detailalamat;
    EditText detailkondisi;
    EditText detaillongitude;
    EditText detaillatitude;
    EditText namapenolong;
    EditText nohppenolong;
    EditText detailidhelp;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId,userId2;

    Button konfirmasi,selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_permintaanbantuan);

        detailnama = findViewById(R.id.detailfullname);
        detailketerangan= findViewById(R.id.detailhelpinformasi);
        detailtelepon =findViewById(R.id.detailRegisnohp);
        detailemail = findViewById(R.id.detailhelpemail);
        detailalamat = findViewById(R.id.detailhelpalamat);
        detailkondisi = findViewById(R.id.detailkondisi);
        namapenolong = findViewById(R.id.namapenolong);
        nohppenolong = findViewById(R.id.nohppenolong);
        detaillatitude = findViewById(R.id.detailLatitude);
        detaillongitude = findViewById(R.id.detailLongitude);
        detailidhelp = findViewById(R.id.detailidhelp);

        konfirmasi = findViewById(R.id.terima);
        selesai = findViewById(R.id.selesai);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        userId = fAuth.getCurrentUser().getUid();



        client = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detailmap);


        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                namapenolong.setText(documentSnapshot.getString("Fullname"));
                nohppenolong.setText(documentSnapshot.getString("PhoneNumber"));
            }
        });

        ambildata();

                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        Double detailllatitude = Double.parseDouble(getIntent().getStringExtra("dlatitude"));
                        Double detailllongitude = Double.parseDouble(getIntent().getStringExtra("dlongitude"));

                        Log.e("Detail Longitude",""+detaillongitude);
                        Log.e("Detail Latitude",""+detaillatitude);

                        LatLng latLng = new LatLng(detailllatitude,detailllongitude);
                        MarkerOptions options = new MarkerOptions().position(latLng).title("Disini");

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                        googleMap.addMarker(options);
                    }
                });

                selesai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hapusdokumen();
                    }
                });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = fAuth.getCurrentUser();
                DocumentReference df = fStore.collection("Needhelp").document(user.getUid());
                Map<String,Object> userInfo = new HashMap<>();
                userInfo.put("NamaLengkap",detailnama.getText().toString());
                userInfo.put("NoHP",detailtelepon.getText().toString());
                userInfo.put("Email",detailemail.getText().toString());
                userInfo.put("Keterangan",detailketerangan.getText().toString());
                userInfo.put("AlamatDetail",detailalamat.getText().toString());
                userInfo.put("Longitude",detaillongitude.getText().toString());
                userInfo.put("Latitude",detaillatitude.getText().toString());
                userInfo.put("NamaPenolong",namapenolong.getText().toString());
                userInfo.put("NoHPpenolong",nohppenolong.getText().toString());
                userInfo.put("Konfirmasi","1");
                df.set(userInfo);

                Toast.makeText(detail_permintaanbantuan.this, "Kamu Menerima", Toast.LENGTH_SHORT).show();
                Toast.makeText(detail_permintaanbantuan.this, "Tolong jangan keluar dari halaman ini", Toast.LENGTH_SHORT).show();
                fStore.collection("Needhelp").document(userId2).delete();
                konfirmasi.setVisibility(View.GONE);
                selesai.setVisibility(View.VISIBLE);

            }
        });
    }

    private void ambildata() {
        if (getIntent().hasExtra("dname") && getIntent().hasExtra("dbantuan")) {
            String datanama = getIntent().getStringExtra("dname");
            String datakerangan = getIntent().getStringExtra("dbantuan");
            String datanotelepon = getIntent().getStringExtra("dtelepon");
            String dataemail = getIntent().getStringExtra("demail");
            String dataalamatdetail = getIntent().getStringExtra("dalamatdetail");
            String datakondisi = getIntent().getStringExtra("dkondisi");
            String datalongitude = getIntent().getStringExtra("dlongitude");
            String datalatitude = getIntent().getStringExtra("dlatitude");
            userId2 = getIntent().getStringExtra("didhelp");

            detailnama.setText(datanama);
            detailketerangan.setText(datakerangan);
            detailtelepon.setText(datanotelepon);
            detailemail.setText(dataemail);
            detailalamat.setText(dataalamatdetail);
            detailkondisi.setText(datakondisi);

            detaillongitude.setText(datalongitude);
            detaillatitude.setText(datalatitude);

        }
    }

    private void hapusdokumen(){
       fStore.collection("Needhelp").document(userId).delete();
        Toast.makeText(detail_permintaanbantuan.this, "Terima Kasih Telah Membantu", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), Reguler_Menu.class));
    }
}