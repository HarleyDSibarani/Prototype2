package com.example.prototype2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.prototype2.adapter.MyAdapterWorkshop;
import com.example.prototype2.adapter.MyAdapterWorkshopList;
import com.example.prototype2.model.needworkshop;
import com.example.prototype2.model.workshoplist;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class list_workshop extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<workshoplist> WorkshoplistArrayList;
    MyAdapterWorkshopList myAdapterWorkshopList;
    FirebaseFirestore fStore;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swiperefreshworkshoplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_workshop);

        swiperefreshworkshoplist = findViewById(R.id.swiperefreshworkshoplist);

        recyclerView = findViewById(R.id.recyclerviewworkshoplist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        fStore = FirebaseFirestore.getInstance();
        WorkshoplistArrayList = new ArrayList<workshoplist>();
        myAdapterWorkshopList = new MyAdapterWorkshopList(list_workshop.this,WorkshoplistArrayList);

        recyclerView.setAdapter(myAdapterWorkshopList);

        EventChangeListener();

        swiperefreshworkshoplist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                myAdapterWorkshopList.notifyDataSetChanged();

                swiperefreshworkshoplist.setRefreshing(false);
            }
        });
    }

    private void refresh(){
        Toast.makeText(this, "Diperbarui", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,list_workshop.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
        overridePendingTransition(0,0);
    }

        private void EventChangeListener() {
            fStore.collection("Workshop").orderBy("NamaBengkel", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                if (progressDialog.isShowing());
                                progressDialog.dismiss();
                                Log.e("Firestore Error",error.getMessage());
                                return;
                            }

                            for (DocumentChange dc : value.getDocumentChanges()){
                                if (dc.getType() == DocumentChange.Type.ADDED){
                                    WorkshoplistArrayList.add(dc.getDocument().toObject(workshoplist.class));
                                }
                                myAdapterWorkshopList.notifyDataSetChanged();
                                if (progressDialog.isShowing());
                                progressDialog.dismiss();
                            }

                        }
                    });
        }
}