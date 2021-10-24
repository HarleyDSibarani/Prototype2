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
import com.example.prototype2.detail_workshop;
import com.example.prototype2.model.needworkshop;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyAdapterWorkshop extends RecyclerView.Adapter<MyAdapterWorkshop.MyViewHolder> {
    Context context;
    ArrayList<needworkshop> WorkshopArrayList;
    FusedLocationProviderClient client;

    public MyAdapterWorkshop(Context context, ArrayList<needworkshop> WorkshopArrayList) {
        this.context = context;
        this.WorkshopArrayList = WorkshopArrayList;
        client = LocationServices.getFusedLocationProviderClient(context);
    }

        @NonNull
        @Override
        public MyAdapterWorkshop.MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(context).inflate(R.layout.item_user_workshop, parent, false);

            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder (@NonNull MyAdapterWorkshop.MyViewHolder holder,int position){

            needworkshop needworkshop = WorkshopArrayList.get(position);
            Double firebaseLatitude = Double.parseDouble(needworkshop.getLatitude());
            Double firebaseLongitude = Double.parseDouble(needworkshop.getLongitude());

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

            holder.namaworkshop.setText(needworkshop.getNamaLengkap());
            holder.bantuanworkshop.setText(needworkshop.getKeterangan());
            holder.teleponpemintabantuanworkshop.setText(needworkshop.getNoHP());
            holder.alamatdetailpemintabantuanworkshop.setText(needworkshop.getAlamatDetail());
            holder.detailkondisiworkshop.setText(needworkshop.getKondisi());

            holder.detailLongitude.setText(needworkshop.getLongitude());
            holder.detailLatitude.setText(needworkshop.getLatitude());

            holder.namaworkshop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.namaworkshop.getContext(), detail_workshop.class);
                    intent.putExtra("dworkshopname", needworkshop.getNamaLengkap());
                    intent.putExtra("dworkshopbantuan", needworkshop.getKeterangan());
                    intent.putExtra("dworkshoptelepon", needworkshop.getNoHP());
                    intent.putExtra("dworkshopalamatdetail", needworkshop.getAlamatDetail());
                    intent.putExtra("dworkshopkondisi", needworkshop.getKondisi());
                    intent.putExtra("didhelp", needworkshop.getIDhelp());

                    intent.putExtra("dlongitude", needworkshop.getLongitude());
                    intent.putExtra("dlatitude", needworkshop.getLatitude());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.namaworkshop.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount () {
            return WorkshopArrayList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView namaworkshop, bantuanworkshop, teleponpemintabantuanworkshop, alamatdetailpemintabantuanworkshop, detailkondisiworkshop, detailLongitude, detailLatitude, item_jawabanjarak,idhelp;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                namaworkshop = itemView.findViewById(R.id.item_namabantuanworkshop);
                bantuanworkshop = itemView.findViewById(R.id.item_jawabanbantuanworkshop);
                teleponpemintabantuanworkshop = itemView.findViewById(R.id.item_nohpworkshop);
                alamatdetailpemintabantuanworkshop = itemView.findViewById(R.id.item_alamatdetailworkshop);
                detailkondisiworkshop = itemView.findViewById(R.id.item_kondisidetailworkshop);
                idhelp = itemView.findViewById(R.id.idhelp);

                detailLongitude = itemView.findViewById(R.id.item_longitudeworkshop);
                detailLatitude = itemView.findViewById(R.id.item_latitudeworkshop);

                item_jawabanjarak = itemView.findViewById(R.id.item_jawabanjarakworkshop);
            }
        }
    }

