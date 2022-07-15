package com.example.kmc.CollectorAdapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.CLogin.CollectorGroundingUserDetails;
import com.example.kmc.Individual;
import com.example.kmc.R;

import java.util.ArrayList;
import java.util.Locale;

public class searchAdapterCollector extends RecyclerView.Adapter<searchAdapterCollector.myviewholder>
{
    ArrayList<Individual> datalist;
    public searchAdapterCollector(ArrayList<Individual> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public searchAdapterCollector.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_search,parent,false);
        return new searchAdapterCollector.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchAdapterCollector.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getStatus());
        holder.t3.setText("Preferred Unit: "+datalist.get(position).getPreferredUnit());
        holder.t4.setText("DB Account Amount: "+datalist.get(position).getDbAccount());
        holder.t5.setText("Approved Amount: "+datalist.get(position).getApprovalAmount());
        holder.t6.setText("Village: "+datalist.get(position).getVillage());

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

