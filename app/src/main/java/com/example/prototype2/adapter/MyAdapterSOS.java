package com.example.prototype2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototype2.R;
import com.example.prototype2.detail_permintaanbantuan;
import com.example.prototype2.detail_sos;
import com.example.prototype2.model.SOS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyAdapterSOS extends RecyclerView.Adapter<MyAdapterSOS.MyViewHolder> {

    Context context;
    ArrayList<SOS> SOSArraylist;
    FusedLocationProviderClient client;

    public MyAdapterSOS(Context context, ArrayList<SOS> SOSArraylist) {
        this.context = context;
        this.SOSArraylist = SOSArraylist;
        client = LocationServices.getFusedLocationProviderClient(context);
    }

    @NonNull
    @Override
    public MyAdapterSOS.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_user_sos,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterSOS.MyViewHolder holder, int position) {

        SOS sos = SOSArraylist.get(position);
        Double firebaseLatitude = Double.parseDouble(sos.getLatitude());
        Double firebaseLongitude = Double.parseDouble(sos.getLongitude());

        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    float[] results = new float[1];
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                            firebaseLatitude, firebaseLongitude,
                            results);
                    if (results[0] > 5000){
                        //holder.itemView.setVisibility(View.GONE);
//                            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                        params.height = 0;
                        params.width = 0;
                        holder.itemView.setLayoutParams(params);
                    }

                    else {
                        DecimalFormat df = new DecimalFormat("#.##");
                        String result = df.format(results[0] / 1000);
                        holder.itemView.setVisibility(View.VISIBLE);
//                            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        holder.jawabanjarak.setText(result + " KM");
                    }
                }
            }
        });

        holder.namasos.setText(sos.getNamaLengkap());
        holder.nohpsos.setText(sos.getNoHP());
        holder.longitudesos.setText(sos.getLongitude());
        holder.latitudesos.setText(sos.getLatitude());

        holder.namasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.namasos.getContext(), detail_sos.class);
                intent.putExtra("dsosnama",sos.getNamaLengkap());
                intent.putExtra("dsosnohp",sos.getNoHP());
                intent.putExtra("dlongitude",sos.getLongitude());
                intent.putExtra("dlatitude",sos.getLatitude());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.namasos.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SOSArraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namasos, nohpsos, longitudesos, latitudesos, jarak, jawabanjarak;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namasos = itemView.findViewById(R.id.item_jawabannamasos);
            nohpsos = itemView.findViewById(R.id.item_jawabannohpsos);
            latitudesos = itemView.findViewById(R.id.item_latitudesos);
            longitudesos = itemView.findViewById(R.id.item_longitudesos);

            jawabanjarak = itemView.findViewById(R.id.item_jawabanjarak);


        }
    }

}
