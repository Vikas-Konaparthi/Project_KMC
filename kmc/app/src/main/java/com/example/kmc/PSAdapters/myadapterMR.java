package com.example.kmc.PSAdapters;

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

import com.example.kmc.Individual;
import com.example.kmc.PSLogin.userDetails;
import com.example.kmc.R;

import java.util.ArrayList;
import java.util.Locale;

public class myadapterMR extends RecyclerView.Adapter<myadapterMR.myviewholder> {
    ArrayList<Individual> datalist;

    public myadapterMR(ArrayList<Individual> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myadapterMR.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report, parent, false);
        return new myadapterMR.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText("Name: " + datalist.get(position).getName());
        holder.t2.setText("Aadhar Number: " + datalist.get(position).getAadhar());
        holder.t3.setText("Preferred Unit: " + datalist.get(position).getPreferredUnit());
        holder.t4.setText("DB Account Amount: " + datalist.get(position).getDbAccount());
        holder.t5.setText("Approved Amount: " + datalist.get(position).getApprovalAmount());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            t5 = itemView.findViewById(R.id.t5);

        }
    }
}