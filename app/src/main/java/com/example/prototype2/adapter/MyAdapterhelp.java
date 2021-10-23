package com.example.prototype2.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototype2.AnotherUser_HelpForm;
import com.example.prototype2.R;
import com.example.prototype2.detail_permintaanbantuan;
import com.example.prototype2.list_userforhelp;
import com.example.prototype2.model.needhelp;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class MyAdapterhelp extends RecyclerView.Adapter<MyAdapterhelp.MyViewHolder> {

    Context context;
    ArrayList<needhelp> userArrayList;
    FusedLocationProviderClient client;


    public MyAdapterhelp(Context context, ArrayList<needhelp> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
        client = LocationServices.getFusedLocationProviderClient(context);
    }


    @NonNull
    @Override
    public MyAdapterhelp.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_userforhelp, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterhelp.MyViewHolder holder, int position) {
        needhelp needhelp = userArrayList.get(position);
        Double firebaseLatitude = Double.parseDouble(needhelp.getLatitude());
        Double firebaseLongitude = Double.parseDouble(needhelp.getLongitude());

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

                    else{
                        DecimalFormat df = new DecimalFormat("#.##");
                        String result = df.format(results[0]/1000);
                        holder.itemView.setVisibility(View.VISIBLE);
//                            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        holder.item_jawabanjarak.setText(result +" KM");
                    }
                }
            }
        });

        holder.nama.setText(needhelp.getNamaLengkap());
        holder.bantuan.setText(needhelp.getKeterangan());
        holder.teleponpemintabantuan.setText(needhelp.getNoHP());
        holder.emailpemintabantuan.setText(needhelp.getEmail());
        holder.alamatdetailpemintabantuan.setText(needhelp.getAlamatdetail());
        holder.detailkondisi.setText(needhelp.getKondisi());;

        holder.nama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.nama.getContext(), detail_permintaanbantuan.class);
                intent.putExtra("dname", needhelp.getNamaLengkap());
                intent.putExtra("dbantuan", needhelp.getKeterangan());
                intent.putExtra("dtelepon", needhelp.getNoHP());
                intent.putExtra("demail", needhelp.getEmail());
                intent.putExtra("dalamatdetail", needhelp.getAlamatdetail());
                intent.putExtra("dkondisi", needhelp.getKondisi());
                intent.putExtra("didhelp", needhelp.getIDhelp());

                intent.putExtra("dlongitude", needhelp.getLongitude());
                intent.putExtra("dlatitude", needhelp.getLatitude());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.nama.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama, bantuan, teleponpemintabantuan, emailpemintabantuan, alamatdetailpemintabantuan, detailkondisi, detailLongitude, detailLatitude, item_jawabanjarak, idhelp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.item_namabantuan);
            bantuan = itemView.findViewById(R.id.item_jawabanbantuan);
            teleponpemintabantuan = itemView.findViewById(R.id.item_no_telepon);
            emailpemintabantuan = itemView.findViewById(R.id.item_email);
            alamatdetailpemintabantuan = itemView.findViewById(R.id.item_alamatdetail);
            detailkondisi = itemView.findViewById(R.id.item_kondisidetail);
            item_jawabanjarak = itemView.findViewById(R.id.item_jawabanjaraklat);
            idhelp = itemView.findViewById(R.id.idhelp);

            detailLongitude = itemView.findViewById(R.id.item_longitude);
            detailLatitude = itemView.findViewById(R.id.item_latitude);
        }
    }

}
