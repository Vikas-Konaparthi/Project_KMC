package com.example.kmc.SOAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.kmc.Individual;
import com.example.kmc.PSAdapters.myadapter;
import com.example.kmc.R;

import java.util.ArrayList;
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

import com.example.kmc.SOLogin.SOUserDetails;
import com.example.kmc.Vendor;
import com.example.kmc.PSLogin.userDetails;
import com.example.kmc.R;

import java.util.ArrayList;
import java.util.Locale;

public class vendorAdapter extends RecyclerView.Adapter<vendorAdapter.myviewholder> {
    ArrayList<Vendor> datalist;

    public vendorAdapter(ArrayList<Vendor> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public vendorAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow3,parent,false);
        return new myviewholder(view);
    }

    public void onBindViewHolder(@NonNull vendorAdapter.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(datalist.get(position).getAgencyName());
        holder.t6.setText("Unit: "+datalist.get(position).getUnit());
        holder.t4.setText("Name: "+datalist.get(position).getVendorName());
        holder.t3.setText("Bank Name: "+datalist.get(position).getVendorBankName());
        holder.t2.setText("Bank ACC Number: "+datalist.get(position).getVendorBankAcc());
        holder.t5.setText("Bank IFSC: "+datalist.get(position).getVendorBankIFSC());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        public TextView t1;
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
