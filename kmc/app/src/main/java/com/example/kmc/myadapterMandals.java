package com.example.kmc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.CLogin.CollectorUserDetails;
import com.example.kmc.CLogin.CollectorVillageSelection;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myadapterMandals extends RecyclerView.Adapter<myadapterMandals.myviewholder>{
    ArrayList<District> datalist;
    String district;
    String aadhar;

    public myadapterMandals(ArrayList<District> datalist, String district,String aadhar) {
        this.datalist = datalist;
        this.district=district;
        this.aadhar=aadhar;
    }

    @NonNull
    @Override
    public myadapterMandals.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow2,parent,false);
        return new myadapterMandals.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterMandals.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(datalist.get(position).getUid());


        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.t1.getContext(), CollectorVillageSelection.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("mandal",datalist.get(position).getUid());
                i.putExtra("district",district);
                i.putExtra("aadhar",aadhar);
                holder.t1.getContext().startActivity(i);
            }
        });
    }




    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t1;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
        }
    }
}
