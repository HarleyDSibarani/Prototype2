package com.example.prototype2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class detail_workshoplist extends AppCompatActivity {

    TextView detailnamaworkshoplist;
    TextView detailnohpworkshoplist;
    TextView detailinformasiworkshoplist;
    TextView detailalamatworkshoplist;
    TextView detailjenisworkshop;
    TextView detaillongitude;
    TextView detaillatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_workshop);

        detailnamaworkshoplist = (TextView) findViewById(R.id.detailnamaworkshoplist);
        detailnohpworkshoplist = (TextView) findViewById(R.id.detailnohpworkshoplist);
        detailinformasiworkshoplist = (TextView) findViewById(R.id.detailinformasiworkshoplist);
        detailalamatworkshoplist = (TextView) findViewById(R.id.detailalamatworkshoplist);
        detailjenisworkshop = (TextView) findViewById(R.id.detailjenisworkshop);
        detaillatitude = (TextView) findViewById(R.id.detailLatitude);
        detaillongitude = (TextView) findViewById(R.id.detailLongitude);

        ambildata();
    }

    private void ambildata() {
        if (getIntent().hasExtra("dworkshopnamelist") && getIntent().hasExtra("dworkshopdeskripsilist")) {
            String datanamaworkshoplist = getIntent().getStringExtra("dworkshopnamelist");
            String datakeranganworkshoplist = getIntent().getStringExtra("dworkshopdeskripsilist");
            String datanoteleponworkshoplist = getIntent().getStringExtra("dworkshopteleponlist");
            String dataalamatdetailworkshoplist = getIntent().getStringExtra("dworkshopalamatdetaillist");
            String datajenisworkshop = getIntent().getStringExtra("dworkshopjenis");
            String datalongitude = getIntent().getStringExtra("dlongitude");
            String datalatitude = getIntent().getStringExtra("dlatitude");

            detailnamaworkshoplist.setText(datanamaworkshoplist);
            detailinformasiworkshoplist.setText(datakeranganworkshoplist);
            detailnohpworkshoplist.setText(datanoteleponworkshoplist);
            detailalamatworkshoplist.setText(dataalamatdetailworkshoplist);
            detailjenisworkshop.setText(datajenisworkshop);

            detaillongitude.setText(datalongitude);
            detaillatitude.setText(datalatitude);

        }
    }
}
