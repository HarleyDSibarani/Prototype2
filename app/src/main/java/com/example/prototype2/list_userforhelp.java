package com.example.prototype2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prototype2.adapter.MyAdapterhelp;
import com.example.prototype2.model.needhelp;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static java.util.Collection.*;

public class list_userforhelp extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<needhelp> userArrayList;
    MyAdapterhelp myAdapterhelp;
    FirebaseFirestore fStore;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swiperefreshuserhelp;
    FusedLocationProviderClient client;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_userforhelp);

        swiperefreshuserhelp = findViewById(R.id.swiperefreshuserhelp);

        recyclerView = findViewById(R.id.recyclerviewuserhelp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");


        fStore = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<needhelp>();
        myAdapterhelp = new MyAdapterhelp(list_userforhelp.this, userArrayList);

        recyclerView.setAdapter(myAdapterhelp);

        recyclerView.getAdapter().notifyDataSetChanged();
        eventChangeListener();

        swiperefreshuserhelp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                myAdapterhelp.notifyDataSetChanged();
                swiperefreshuserhelp.setRefreshing(false);
            }
        });

    }

    private void refresh() {
        Toast.makeText(this, "Diperbarui", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, list_userforhelp.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
    }

    private void eventChangeListener() {
        fStore.collection("Needhelp").orderBy("Kondisi", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) ;
                            progressDialog.dismiss();
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                userArrayList.add(dc.getDocument().toObject(needhelp.class));
                            }
                            myAdapterhelp.notifyDataSetChanged();
                            if (progressDialog.isShowing()) ;
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}