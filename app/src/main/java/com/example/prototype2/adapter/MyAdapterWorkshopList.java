package com.example.prototype2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototype2.R;
import com.example.prototype2.detail_permintaanbantuan;
import com.example.prototype2.detail_workshop;
import com.example.prototype2.detail_workshoplist;
import com.example.prototype2.model.workshoplist;
import com.example.prototype2.model.workshoplist;

import java.util.ArrayList;

public class MyAdapterWorkshopList extends RecyclerView.Adapter<MyAdapterWorkshopList.MyViewHolder> {
    Context context;
    ArrayList<workshoplist> WorkshoplistArrayList;

    public MyAdapterWorkshopList(Context context, ArrayList<workshoplist> WorkshoplistArrayList) {
        this.context = context;
        this.WorkshoplistArrayList = WorkshoplistArrayList;
    }

    @NonNull
    @Override
    public MyAdapterWorkshopList.MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_workshop, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull MyAdapterWorkshopList.MyViewHolder holder,int position){

        workshoplist workshoplist = WorkshoplistArrayList.get(position);
        holder.namaworkshoplist.setText(workshoplist.getNamaBengkel());
        holder.deskripsiworkshop.setText(workshoplist.getKeterangan());
        holder.teleponpemintabantuanworkshoplist.setText(workshoplist.getNoHP());
        holder.alamatdetailpemintabantuanworkshoplist.setText(workshoplist.getAlamatDetail());
        holder.detailjenisworkshop.setText(workshoplist.getJenisBengkel());

        holder.detailLongitude.setText(workshoplist.getLongitude());
        holder.detailLatitude.setText(workshoplist.getLatitude());

        holder.namaworkshoplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.namaworkshoplist.getContext(), detail_workshoplist.class);
                intent.putExtra("dworkshopnamelist", workshoplist.getNamaBengkel());
                intent.putExtra("dworkshopdeskripsilist", workshoplist.getKeterangan());
                intent.putExtra("dworkshopteleponlist", workshoplist.getNoHP());
                intent.putExtra("dworkshopalamatdetaillist", workshoplist.getAlamatDetail());
                intent.putExtra("dworkshopjenis", workshoplist.getJenisBengkel());

                intent.putExtra("dlongitude", workshoplist.getLongitude());
                intent.putExtra("dlatitude", workshoplist.getLatitude());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.namaworkshoplist.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return WorkshoplistArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaworkshoplist, deskripsiworkshop, teleponpemintabantuanworkshoplist, alamatdetailpemintabantuanworkshoplist, detailjenisworkshop, detailLongitude, detailLatitude;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namaworkshoplist = itemView.findViewById(R.id.item_namalistworkshop);
            deskripsiworkshop = itemView.findViewById(R.id.item_jawabandeskripsibengkel);
            teleponpemintabantuanworkshoplist = itemView.findViewById(R.id.item_nohpworkshoplist);
            alamatdetailpemintabantuanworkshoplist = itemView.findViewById(R.id.item_alamatdetailworkshoplist);
            detailjenisworkshop = itemView.findViewById(R.id.item_jawabanjenisworkshop);

            detailLongitude = itemView.findViewById(R.id.item_longitudeworkshop);
            detailLatitude = itemView.findViewById(R.id.item_latitudeworkshop);

        }
    }
}

