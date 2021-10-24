package com.example.prototype2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class detail_workshop extends AppCompatActivity {
    private SupportMapFragment mapFragment;

    TextView detailnamaworkshop;
    TextView detailnohpworkshop;
    TextView detailinformasiworkshop;
    TextView detailalamatworkshop;
    TextView detailkondisiworkshop;
    TextView detaillongitude;
    TextView detaillatitude;
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
        setContentView(R.layout.activity_detail_workshop);

        detailnamaworkshop = (TextView) findViewById(R.id.detailnamaworkshop);
        detailnohpworkshop = (TextView) findViewById(R.id.detailnohpworkshop);
        detailinformasiworkshop = (TextView) findViewById(R.id.detailinformasiworkshop);
        detailalamatworkshop = (TextView) findViewById(R.id.detailalamatworkshop);
        detailkondisiworkshop = (TextView) findViewById(R.id.detailkondisiworkshop);
        detaillatitude = (TextView) findViewById(R.id.detailLatitude);
        detaillongitude = (TextView) findViewById(R.id.detailLongitude);
        namapenolong = findViewById(R.id.namapenolong);
        nohppenolong = findViewById(R.id.nohppenolong);
        detailidhelp = findViewById(R.id.detailidhelp);

        konfirmasi = findViewById(R.id.terima);
        selesai = findViewById(R.id.selesai);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

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
                bantuanselesai();
            }
        });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = fAuth.getCurrentUser();
                DocumentReference df = fStore.collection("needworkshop").document(user.getUid());
                Map<String,Object> userInfo = new HashMap<>();
                userInfo.put("NamaLengkap",detailnamaworkshop.getText().toString());
                userInfo.put("NoHP",detailnohpworkshop.getText().toString());
                userInfo.put("Keterangan",detailinformasiworkshop.getText().toString());
                userInfo.put("AlamatDetail",detailalamatworkshop.getText().toString());
                userInfo.put("NamaPenolong",namapenolong.getText().toString());
                userInfo.put("NoHPpenolong",nohppenolong.getText().toString());
                userInfo.put("Konfirmasi","1");
                df.set(userInfo);

                Toast.makeText(detail_workshop.this, "Kamu Menerima", Toast.LENGTH_SHORT).show();
                Toast.makeText(detail_workshop.this, "Tolong jangan keluar dari halaman ini", Toast.LENGTH_SHORT).show();
                fStore.collection("needworkshop").document(userId2).delete();

                Log.e("idhelp",""+userId2);

                konfirmasi.setVisibility(View.GONE);
                selesai.setVisibility(View.VISIBLE);

            }
        });
    }

        private void ambildata() {
            if (getIntent().hasExtra("dworkshopname") && getIntent().hasExtra("dworkshopbantuan")) {
                String datanamaworkshop = getIntent().getStringExtra("dworkshopname");
                String datakeranganworkshop = getIntent().getStringExtra("dworkshopbantuan");
                String datanoteleponworkshop = getIntent().getStringExtra("dworkshoptelepon");
                String dataalamatdetailworkshop = getIntent().getStringExtra("dworkshopalamatdetail");
                String datakondisiworkshop = getIntent().getStringExtra("dworkshopkondisi");
                String datalongitude = getIntent().getStringExtra("dlongitude");
                String datalatitude = getIntent().getStringExtra("dlatitude");
                userId2 = getIntent().getStringExtra("didhelp");

                Log.e("idhelp",""+userId2);

                detailnamaworkshop.setText(datanamaworkshop);
                detailinformasiworkshop.setText(datakeranganworkshop);
                detailnohpworkshop.setText(datanoteleponworkshop);
                detailalamatworkshop.setText(dataalamatdetailworkshop);
                detailkondisiworkshop.setText(datakondisiworkshop);

                detaillongitude.setText(datalongitude);
                detaillatitude.setText(datalatitude);

            }
        }

    private void bantuanselesai(){
        fStore.collection("needworkshop").document(userId).delete();
        Toast.makeText(detail_workshop.this, "Terima Kasih Telah Membantu", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), Workshop_Menu.class));
    }
    }
