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

import com.example.kmc.CLogin.CollectorGroundingUserDetails;
import com.example.kmc.CLogin.CollectorMandalOverview;
import com.example.kmc.MandalElements;
import com.example.kmc.R;

import java.util.ArrayList;

public class myadapterConstituencyOverView extends RecyclerView.Adapter<myadapterConstituencyOverView.myviewholder>
{
    ArrayList<MandalElements> datalist;
    String district;

    public myadapterConstituencyOverView(ArrayList<MandalElements> datalist,String district) {
        this.datalist = datalist;
        this.district=district;
    }

    @NonNull
    @Override
    public myadapterConstituencyOverView.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_overview,parent,false);
        return new myadapterConstituencyOverView.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterConstituencyOverView.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(datalist.get(position).getMandalName());
        holder.t2.setText("Total Registered: "+datalist.get(position).getTotalRegistered());
        holder.t3.setText("Total Selected: "+datalist.get(position).getTotalSelected());
        holder.t4.setText("Total Approved Amount: "+datalist.get(position).getTotalApprovedAmount());
        holder.t5.setText("DB Amount: "+datalist.get(position).getDbAccountAmount());
        holder.t6.setText("Total Grounded: "+datalist.get(position).getGrounding());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.t1.getContext(), CollectorMandalOverview.class);
                i.putExtra("district",district);
                i.putExtra("constituency",datalist.get(position).getMandalName());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.t1.getContext().startActivity(i);
      //          ((Activity)holder.t1.getContext()).finish();
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

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            t5=itemView.findViewById(R.id.t5);
            t6=itemView.findViewById(R.id.t6);

        }
    }
}

