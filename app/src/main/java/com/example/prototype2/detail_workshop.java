package com.example.prototype2;

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

public class detail_workshop extends AppCompatActivity {
    private SupportMapFragment mapFragment;

    TextView detailnamaworkshop;
    TextView detailnohpworkshop;
    TextView detailinformasiworkshop;
    TextView detailalamatworkshop;
    TextView detailkondisiworkshop;
    TextView detaillongitude;
    TextView detaillatitude;

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

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detailmap);

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

                detailnamaworkshop.setText(datanamaworkshop);
                detailinformasiworkshop.setText(datakeranganworkshop);
                detailnohpworkshop.setText(datanoteleponworkshop);
                detailalamatworkshop.setText(dataalamatdetailworkshop);
                detailkondisiworkshop.setText(datakondisiworkshop);

                detaillongitude.setText(datalongitude);
                detaillatitude.setText(datalatitude);

            }
        }
    }
