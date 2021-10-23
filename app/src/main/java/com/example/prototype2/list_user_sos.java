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

import com.example.prototype2.adapter.MyAdapterSOS;
import com.example.prototype2.adapter.MyAdapterhelp;
import com.example.prototype2.model.SOS;
import com.example.prototype2.model.needhelp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class list_user_sos extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<SOS> userArrayList;
    MyAdapterSOS myAdapterSOS;
    FirebaseFirestore fStore;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swiperefreshsos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_sos);

        swiperefreshsos = findViewById(R.id.swiperefreshsos);

        recyclerView = findViewById(R.id.recyclerViewsos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        fStore = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<SOS>();
        myAdapterSOS = new MyAdapterSOS(list_user_sos.this,userArrayList);

        recyclerView.setAdapter(myAdapterSOS);

        EventChangeListener();

        swiperefreshsos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                myAdapterSOS.notifyDataSetChanged();

                swiperefreshsos.setRefreshing(false);
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

    private void EventChangeListener() {
        fStore.collection("SOS").orderBy("NamaLengkap", Query.Direction.ASCENDING)
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
                                userArrayList.add(dc.getDocument().toObject(SOS.class));
                            }
                            myAdapterSOS.notifyDataSetChanged();
                            if (progressDialog.isShowing());
                            progressDialog.dismiss();
                        }

                    }
                });
    }
}