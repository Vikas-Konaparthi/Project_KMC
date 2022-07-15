package com.example.kmc.COverview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.CLogin.CollectorListOfBen;
import com.example.kmc.CLogin.CollectorVillageOverview;
import com.example.kmc.MandalElements;
import com.example.kmc.R;

import java.util.ArrayList;

public class myadapterVillageOverview extends RecyclerView.Adapter<myadapterVillageOverview.myviewholder>
{
    ArrayList<MandalElements> datalist;
    String district;

    public myadapterVillageOverview(ArrayList<MandalElements> datalist, String district) {
        this.datalist = datalist;
        this.district = district;
    }

    @NonNull
    @Override
    public myadapterVillageOverview.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_overview,parent,false);
        return new myadapterVillageOverview.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterVillageOverview.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(datalist.get(position).getMandalName());
        holder.t2.setText("Registered: "+datalist.get(position).getTotalRegistered());
        holder.t3.setText("Selected: "+datalist.get(position).getTotalSelected());
        holder.t4.setText("Amount Transferred to DB:"+datalist.get(position).getTotalDbAmount()+"L");
        holder.t5.setText("Approved Amount: "+datalist.get(position).getTotalApprovedAmount()+"L");
        holder.t6.setText("DB Amount: "+datalist.get(position).getDbAccountAmount()+"L");
        holder.t7.setText("Grounded: "+datalist.get(position).getGrounding());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.t1.getContext(), CollectorListOfBen.class);
                i.putExtra("village",datalist.get(position).getMandalName());
                i.putExtra("district",district);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.t1.getContext().startActivity(i);
                //         ((Activity)holder.t1.getContext()).finish();
            }
        }) ;
    }




    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        TextView t6;
        TextView t7;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            t5=itemView.findViewById(R.id.t5);
            t6=itemView.findViewById(R.id.t6);
            t7=itemView.findViewById(R.id.t7);

        }
    }
}

